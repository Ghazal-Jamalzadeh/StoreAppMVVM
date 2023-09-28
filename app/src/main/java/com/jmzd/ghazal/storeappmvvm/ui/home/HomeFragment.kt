package com.jmzd.ghazal.storeappmvvm.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coil.load
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners.ResponseBannersItem
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount.ResponseDiscountItem
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentHomeBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.ui.home.adapters.BannerAdapter
import com.jmzd.ghazal.storeappmvvm.ui.home.adapters.DiscountAdapter
import com.jmzd.ghazal.storeappmvvm.ui.login.LoginPhoneFragmentDirections
import com.jmzd.ghazal.storeappmvvm.utils.IS_CALLED_VERIFY
import com.jmzd.ghazal.storeappmvvm.utils.extensions.*
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.HomeViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<HomeViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    //snap helper
    private val pagerSnapHelper : PagerSnapHelper by lazy {
        PagerSnapHelper()
    }

    //count down timer
    private lateinit var countDownTimer: CountDownTimer

    @Inject
    lateinit var bannerAdapter : BannerAdapter

    @Inject
    lateinit var discountAdapter : DiscountAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding.apply {
            //navigate to profile
            avatarImg.setOnClickListener{
                findNavController().navigate(R.id.action_to_profile_fragment)
            }
        }
        //observers
        observeProfileLiveData()
        observeBannersLiveData()
        observeDiscountsLiveData()
    }

    //--- observers ---//
    private fun observeProfileLiveData() {
        binding.apply {
            profileViewModel.profileLiveData.observe(viewLifecycleOwner) { response: MyResponse<ResponseProfile>? ->
                when (response) {
                    is MyResponse.Loading -> {
                        avatarLoading.isVisible = true
                    }
                    is MyResponse.Error -> {
                        avatarLoading.isVisible = false

                        root.showSnackBar(response.message!!)
                    }
                    is MyResponse.Success -> {
                        avatarLoading.isVisible = false

                        response.data?.let {
                            //Avatar
                            if (it.avatar != null) {
                                avatarImg.loadImage(it.avatar)
                                avatarBadgeImg.isVisible = false
                            } else {
                                avatarImg.load(R.drawable.placeholder_user)
                                avatarBadgeImg.isVisible = true
                            }
                        }
                    }

                }
            }
        }
    }

    private fun observeBannersLiveData() {
        binding.apply {
            viewModel.bannersLiveData.observe(viewLifecycleOwner) { response: MyResponse<ResponseBanners>? ->
                when (response) {
                    is MyResponse.Loading -> {
                        bannerLoading.changeVisibility(true , bannerList)
                    }
                    is MyResponse.Error -> {
                        bannerLoading.changeVisibility(false , bannerList)

                        root.showSnackBar(response.message!!)
                    }
                    is MyResponse.Success -> {
                        bannerLoading.changeVisibility(false , bannerList)

                        response.data?.let {
                            //banners
                            if (it.isNotEmpty()){
                                initBannerRecycler(it)
                            }
                        }
                    }

                }
            }
        }
    }

    private fun observeDiscountsLiveData() {
        binding.apply {
            viewModel.discountsLiveData.observe(viewLifecycleOwner) { response: MyResponse<ResponseDiscount>? ->
                when (response) {
                    is MyResponse.Loading -> {
                        discountList.showShimmer()
                    }
                    is MyResponse.Error -> {
                        discountList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                    is MyResponse.Success -> {
                        discountList.hideShimmer()

                        response.data?.let { data ->
                            if (data.isNotEmpty()){
                                initDiscountRecycler(data)
                                //Discount
                                data[0].endTime?.let { value : String ->
                                    val endTime = value.split("T")[0]
                                    discountTimer(endTime)
                                    countDownTimer.start()
                                }
                            }else{
                                discountCard.isVisible = false
                            }
                        }
                    }

                }
            }
        }
    }

    //--- recyclers ---//
    private fun initBannerRecycler(data: List<ResponseBannersItem>) {
        bannerAdapter.setData(data)
        binding.bannerList.apply {
            adapter = bannerAdapter
            set3DItem(true)
            setAlpha(true)
            setInfinite(true)
        }
        //Click
        bannerAdapter.setOnItemClickListener {

        }
        //Indicator
        binding.apply {
            pagerSnapHelper.attachToRecyclerView(bannerList)
            bannerIndicator.attachToRecyclerView(bannerList, pagerSnapHelper)
        }
    }

    private fun initDiscountRecycler(data: List<ResponseDiscountItem>) {
        discountAdapter.setData(data)
        binding.discountList.setupRecyclerview(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true), discountAdapter
        )
        //Click
        discountAdapter.setOnItemClickListener {

        }
    }

    //--- timer ---//
    private fun discountTimer(fullDate: String) {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date: Date = formatter.parse(fullDate) as Date
        val currentMillis = System.currentTimeMillis()
        val finalMillis = date.time - currentMillis
        countDownTimer = object : CountDownTimer(finalMillis, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                //Calculate time
                var timer = millisUntilFinished
                val day: Long = TimeUnit.MILLISECONDS.toDays(timer)
                timer -= TimeUnit.DAYS.toMillis(day)
                val hours: Long = TimeUnit.MILLISECONDS.toHours(timer)
                timer -= TimeUnit.HOURS.toMillis(hours)
                val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(timer)
                timer -= TimeUnit.MINUTES.toMillis(minutes)
                val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(timer)
                //View
                try {
                    binding.timerLay.apply {
                        if (day > 0) {
                            dayLay.isVisible = true
                            dayTxt.text = day.toString()
                        } else {
                            dayLay.isVisible = false
                        }
                        hourTxt.text = hours.toString()
                        minuteTxt.text = minutes.toString()
                        secondTxt.text = seconds.toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFinish() {

            }
        }
    }

    //--- life cycle ---//
    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        countDownTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}