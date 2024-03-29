package com.jmzd.ghazal.storeappmvvm.ui.home

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coil.load
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners.ResponseBannersItem
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount.ResponseDiscountItem
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseProducts
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.databinding.DialogCheckVpnBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentHomeBinding
import com.jmzd.ghazal.storeappmvvm.ui.categories.CategoriesFragmentDirections
import com.jmzd.ghazal.storeappmvvm.ui.home.adapters.BannerAdapter
import com.jmzd.ghazal.storeappmvvm.ui.home.adapters.DiscountAdapter
import com.jmzd.ghazal.storeappmvvm.ui.home.adapters.ProductsAdapter
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.constants.PRODUCT
import com.jmzd.ghazal.storeappmvvm.utils.enums.ProductsCategories
import com.jmzd.ghazal.storeappmvvm.utils.extensions.*
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.viewmodel.HomeViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    //binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by activityViewModels<HomeViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    //snap helper
    private val pagerSnapHelper : PagerSnapHelper by lazy {
        PagerSnapHelper()
    }

    //count down timer
    private lateinit var countDownTimer: CountDownTimer

    //adapters
    @Inject lateinit var bannerAdapter : BannerAdapter
    @Inject lateinit var discountAdapter : DiscountAdapter
    @Inject lateinit var mobileProductsAdapter : ProductsAdapter
    @Inject lateinit var shoesProductsAdapter : ProductsAdapter
    @Inject lateinit var stationeryProductsAdapter : ProductsAdapter
    @Inject lateinit var laptopProductsAdapter : ProductsAdapter

    //check vpn
    @Inject
    lateinit var checkVpn: Flow<Boolean>

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

            //restore state
            viewModel.lastScrollState.let {
                scrollLay.onRestoreInstanceState(it)
            }

            //navigate to profile
            avatarImg.setOnClickListener{
                findNavController().navigate(R.id.action_to_profile_fragment)
            }

            //navigate to search
            searchImg.setOnClickListener{
                findNavController().navigate(R.id.action_to_search_fragment)
            }
        }
        //observers
        observeProfileLiveData()
        observeBannersLiveData()
        observeDiscountsLiveData()
        observeProductsLiveData()

        //Check VPN
        lifecycleScope.launch {
            checkVpn.collect {
                if(it) {
                    showVpnDialog()
                }
            }
        }
    }

    override fun onNetworkLost() {
    }

    //--- observers ---//
    private fun observeProfileLiveData() {
        binding.apply {
            profileViewModel.profileLiveData.observe(viewLifecycleOwner) { response: NetworkRequest<ResponseProfile>? ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        avatarLoading.isVisible = true
                    }
                    is NetworkRequest.Error -> {
                        avatarLoading.isVisible = false

                        root.showSnackBar(response.message!!)
                    }
                    is NetworkRequest.Success -> {
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
            viewModel.bannersLiveData.observe(viewLifecycleOwner) { response: NetworkRequest<ResponseBanners>? ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        bannerLoading.changeVisibility(true , bannerList)
                    }
                    is NetworkRequest.Error -> {
                        bannerLoading.changeVisibility(false , bannerList)

                        root.showSnackBar(response.message!!)
                    }
                    is NetworkRequest.Success -> {
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
            viewModel.discountsLiveData.observe(viewLifecycleOwner) { response: NetworkRequest<ResponseDiscount>? ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        discountList.showShimmer()
                    }
                    is NetworkRequest.Error -> {
                        discountList.hideShimmer()
                        root.showSnackBar(response.message!!)
                    }
                    is NetworkRequest.Success -> {
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

    private fun observeProductsLiveData() {
        binding.apply {
            //Mobile
            if (mobileLay.parent != null) {
                val mobileInflate = mobileLay.inflate()
                viewModel.getProductsLiveData(ProductsCategories.MOBILE).observe(viewLifecycleOwner) {
                    handleProductsRequest(it, mobileInflate.findViewById(R.id.mobileProductsList), mobileProductsAdapter)
                }
            }
            //Shoes
            if (shoesLay.parent != null) {
                val shoesInflate = shoesLay.inflate()
                viewModel.getProductsLiveData(ProductsCategories.SHOES).observe(viewLifecycleOwner) {
                    handleProductsRequest(it, shoesInflate.findViewById(R.id.menShoesProductsList), shoesProductsAdapter)
                }
            }
            //Stationery
            val stationeryInflate = stationeryLay.inflate()
            viewModel.getProductsLiveData(ProductsCategories.STATIONERY).observe(viewLifecycleOwner) {
                handleProductsRequest(it, stationeryInflate.findViewById(R.id.stationeryProductsList), stationeryProductsAdapter)
            }
            //Laptop
            val laptopInflate = laptopLay.inflate()
            viewModel.getProductsLiveData(ProductsCategories.LAPTOP).observe(viewLifecycleOwner) {
                handleProductsRequest(it, laptopInflate.findViewById(R.id.laptopProductsList), laptopProductsAdapter)
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
        bannerAdapter.setOnItemClickListener { sendData, type ->
            if (type == PRODUCT) {
                val direction = HomeFragmentDirections.actionToDetailFragment(sendData.toInt())
                findNavController().navigate(direction)
            } else {
                val direction = CategoriesFragmentDirections.actionToCategoriresProductsFragment(sendData)
                findNavController().navigate(direction)
            }
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
            val direction = HomeFragmentDirections.actionToDetailFragment(it)
            findNavController().navigate(direction)
        }
    }

    private fun initProductsRecyclers(data: List<ResponseProducts.SubCategory.Products.Data>, recyclerView: ShimmerRecyclerView, adapter: ProductsAdapter) {
        adapter.setData(data)
        recyclerView.setupRecyclerview(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true),
            adapter
        )
        //Click
        adapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionToDetailFragment(it)
            findNavController().navigate(direction)
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

    //--- Dialog ---//
    private fun showVpnDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogCheckVpnBinding.inflate(layoutInflater)
        dialog.transparentCorners()
        dialog.setContentView(dialogBinding.root)

        dialogBinding.yesBtn.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    //--- other ---//
    private fun handleProductsRequest(
        request: NetworkRequest<ResponseProducts>,
        recyclerView: ShimmerRecyclerView,
        adapter: ProductsAdapter
    ) {
        when (request) {
            is NetworkRequest.Loading -> {
                recyclerView.showShimmer()
            }

            is NetworkRequest.Success -> {
                recyclerView.hideShimmer()
                request.data?.let { data ->
                    data.subCategory?.let { subCats ->
                        subCats.products?.let { products ->
                            products.data?.let { myData ->
                                if (myData.isNotEmpty()) {
                                     initProductsRecyclers(myData, recyclerView, adapter)
                                }
                            }
                        }
                    }
                }
            }

            is NetworkRequest.Error -> {
                recyclerView.hideShimmer()
                binding.root.showSnackBar(request.message!!)
            }
        }
    }


    //--- life cycle ---//
    override fun onPause() {
        super.onPause()
        viewModel.lastScrollState = binding.scrollLay.onSaveInstanceState()
    }

    override fun onStop() {
        super.onStop()
        if (this::countDownTimer.isInitialized){
            countDownTimer.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}