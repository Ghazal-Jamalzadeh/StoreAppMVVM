package com.jmzd.ghazal.storeappmvvm.ui.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.tabs.TabLayout
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.cart.BodyAddToCart
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.databinding.DialogImageBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentDetailBinding
import com.jmzd.ghazal.storeappmvvm.ui.detail.adapters.ImagesAdapter
import com.jmzd.ghazal.storeappmvvm.ui.detail.adapters.PagerAdapter
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.constants.BASE_URL_IMAGE
import com.jmzd.ghazal.storeappmvvm.utils.constants.COLOR_BLACK
import com.jmzd.ghazal.storeappmvvm.utils.constants.COLOR_WHITE
import com.jmzd.ghazal.storeappmvvm.utils.constants.SPECIAL
import com.jmzd.ghazal.storeappmvvm.utils.events.EventBus
import com.jmzd.ghazal.storeappmvvm.utils.events.Events
import com.jmzd.ghazal.storeappmvvm.utils.extensions.*
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.CartViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.multibindings.IntKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    //binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<DetailViewModel>()
    private val cartViewModel by viewModels<CartViewModel>()

    //args
    private val args by navArgs<DetailFragmentArgs>()

    //body
    @Inject
    lateinit var bodyCart : BodyAddToCart

    //adapter
    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    //view pager
    @Inject
    lateinit var pagerAdapter: PagerAdapter

    //timer
    private lateinit var countDownTimer: CountDownTimer

    //other
    private var productId = 0
    private var isNeededToColor = false
    private var likeCount = ""
    private var addedToCart = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Args
        args.let {
            productId = it.productId
        }
        //call api
        if (productId > 0){
            if (isNetworkAvailable){
                viewModel.getDetail(productId)
            }
        }
        //Back
        binding.detailHeaderLay.backImg.setOnClickListener { findNavController().popBackStack() }

        //observers
        observeDetailLiveData()
        observeLikeLiveData()
        observeAddToCartLiveData()
    }
    //--- observers ---//
    private fun observeDetailLiveData() {
        binding.apply {
            viewModel.detailLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        detailLoading.changeVisibility(true, containerLay)
                    }

                    is MyResponse.Success -> {
                        detailLoading.changeVisibility(false, containerLay)
                        response.data?.let { data ->
                            initDetailViews(data)
                        }
                    }

                    is MyResponse.Error -> {
                        detailLoading.changeVisibility(false, containerLay)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeLikeLiveData() {
        binding.detailHeaderLay.apply {
            viewModel.likeLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        favLoading.changeVisibility(true, favImg)
                    }

                    is MyResponse.Success -> {
                        favLoading.changeVisibility(false, favImg)
                        response.data?.let { data ->
                            updateFavUI(data.count!!)
                            binding.detailInfoLay.apply {
                                if (data.count == 1)
                                //rateTxt.text = "${likeCount.toInt() + 1} ${getString(R.string.rate)}"
                                    rateTxt.text = "${rateTxt.text.toString().dropLast(7).toInt() + 1} " +
                                            getString(R.string.rate)
                                else
                                //rateTxt.text = "${likeCount.toInt() - 1} ${getString(R.string.rate)}"
                                    rateTxt.text = "${rateTxt.text.toString().dropLast(7).toInt() - 1} " +
                                            getString(R.string.rate)
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        favLoading.changeVisibility(false, favImg)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }


    private fun observeAddToCartLiveData() {
        binding.detailBottom.apply {
            cartViewModel.addToCartLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        addToCartBtn.enableLoading(true)
                    }

                    is MyResponse.Success -> {
                        addToCartBtn.enableLoading(false)
                        response.data?.let { data ->
                            root.showSnackBar(data.message!!)
                            addedToCart = 1
                            updateAddToCartUI(addToCartBtn)
                            lifecycleScope.launch {
                                EventBus.publish(Events.IsUpdateCart)
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        addToCartBtn.enableLoading(false)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }


    //--- init views ---//
    private fun initDetailViews(data: ResponseDetail) {
        loadImage(data.image!!)
//        PRODUCT_ID = data.id!!
        initDetailHeaderView(data)
        initDetailInfoView(data)
        initDetailTimerView(data)
        setupViewPager()
        initDetailBottomView(data)
    }

    private fun loadImage(data: String) {
        binding.detailHeaderLay.productImg.apply {
            val image = "$BASE_URL_IMAGE$data"
            loadImageWithGlide(image)
            //Click
            setOnClickListener {
                showImageDialog(image)
            }
        }
    }

    private fun showImageDialog(imageUrl: String) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogImageBinding.inflate(layoutInflater)
        dialog.transparentCorners()
        dialog.setContentView(dialogBinding.root)
        //Load image
        dialogBinding.productImg.loadImageWithGlide(imageUrl)
        //Show
        dialog.show()
    }


    private fun initDetailHeaderView(data: ResponseDetail) {
        loadImage(data.image!!)
        //Header
        binding.detailHeaderLay.apply {
            productTitle.text = data.title
            //Desc
            if (data.description.isNullOrEmpty().not()) {
                val info = HtmlCompat.fromHtml(data.description.toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
                productInfo.text = info
            } else {
                productInfo.isVisible = false
            }
            //Colors
            if (data.colors!!.isNotEmpty()) {
                isNeededToColor = true
                setupChip(data.colors.toMutableList())
                //Rtl scrollview
                lifecycleScope.launch {
                    delay(100)
                    colorsScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                }
            } else {
                isNeededToColor = false
                line1.isVisible = false
                colorsTitle.isVisible = false
                colorsScroll.isVisible = false
            }
//            Favorite
            updateFavUI(data.isAddToFavorite!!.toInt())
            favImg.setOnClickListener {
                if (isNetworkAvailable) {
                    viewModel.likeProduct(productId)
                }
            }
            //Images
            if (data.images != null) {
                if (data.images.isNotEmpty()) {
                    data.images.toMutableList().add(0, data.image)
                    initImagesRecycler(data.images)
                }
            }
        }
    }

    private fun setupChip(list: MutableList<ResponseDetail.Color>) {
        list.forEach {
            val chip = Chip(requireContext())
            val drawable = ChipDrawable.createFromAttributes(
                requireContext(), null, 0,
                R.style.DetailChipsBackground
            )
            chip.setChipDrawable(drawable)
            //Color
            val color = if (it.hexCode?.lowercase() == COLOR_WHITE) {
                COLOR_BLACK
            } else {
                it.hexCode
            }
            chip.setTextColor(android.graphics.Color.parseColor(color))
            chip.text = it.title
            chip.id = it.id!!
            chip.setTextAppearanceResource(R.style.DetailChipsText)

            binding.detailHeaderLay.colorsChipGroup.apply {
                addView(chip)
                //Click
                chip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked)
                        bodyCart.colorId = chip.id.toString()
                }
            }
        }
    }

    private fun updateFavUI(count: Int) {
        binding.detailHeaderLay.favImg.apply {
            if (count == 1) setTint(R.color.salmon) else setTint(R.color.gray)
        }
    }

    private fun initImagesRecycler(data: List<String>) {
        imagesAdapter.setData(data)
        binding.detailHeaderLay.productImagesList.setupRecyclerview(LinearLayoutManager(requireContext()), imagesAdapter)
        //Click
        imagesAdapter.setOnItemClickListener {
            loadImage(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailInfoView(data: ResponseDetail) {
        binding.detailInfoLay.apply {
            //brand
            if (data.brand != null) {
                brandTxt.text = data.brand.title?.fa
            } else {
                brandLay.isVisible = false
                line1.isVisible = false
            }
            //Guarantee
            if (data.guarantee.isNullOrEmpty().not()) {
                guaranteeTxt.text = data.guarantee
            } else {
                guaranteeLay.isVisible = false
                line3.isVisible = false
            }
            //General
            categoryTxt.text = data.category?.title
            quantityTxt.text = "${data.quantity} ${getString(R.string.item)}"
            commentsTxt.text = "${data.commentsCount} ${getString(R.string.comment)}"
            likeCount = data.likesCount.toString()
            rateTxt.text = "${data.likesCount} ${getString(R.string.rate)}"
            //Special
            specialTitle.isVisible = data.status == SPECIAL
        }
    }

    private fun initDetailTimerView(data: ResponseDetail) {
        binding.detailTimerLay.apply {
            if (data.discountedPrice?.toInt()!! > 0) {
                if (data.endTime.isNullOrEmpty().not()) {
                    priceDiscountLay.isVisible = true
                    val date = data.endTime!!.split("T")[0]
                    discountTimer(date)
                    countDownTimer.start()
                } else {
                    priceDiscountLay.isVisible = false
                }
                //Discount
                binding.detailBottom.oldPriceTxt.apply {
                    text = data.productPrice.toString().toInt().moneySeparating()
                    paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            } else {
                priceDiscountLay.isVisible = false
            }
        }
    }

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
                    binding.detailTimerLay.timerLay.apply {
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


    private fun setupViewPager() {
        binding.detailPagerLay.apply {
            detailTabLayout.addTab(detailTabLayout.newTab().setText(getString(R.string.comments)))
            detailTabLayout.addTab(detailTabLayout.newTab().setText(getString(R.string.features)))
            detailTabLayout.addTab(detailTabLayout.newTab().setText(getString(R.string.priceChart)))
            //View pager adapter
            detailViewPager.adapter = pagerAdapter
            //Select
            detailTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) detailViewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            //View pager
            detailViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    detailTabLayout.selectTab(detailTabLayout.getTabAt(position))
                }
            })
            //Disable swipe
            detailViewPager.isUserInputEnabled = false
        }
    }

    private fun initDetailBottomView(data: ResponseDetail) {
        addedToCart = data.isAddToCart!!
        binding.detailBottom.apply {
            //Exist in cart
            if (data.isAddToCart == 1) {
                updateAddToCartUI(addToCartBtn)
            }
            //Price
            finalPriceTxt.text = data.finalPrice?.moneySeparating()
            //Click
            addToCartBtn.setOnClickListener {
                if (addedToCart == 0) {
                    if (data.quantity.toString().toInt() > 0) {
                        if (isNeededToColor) {
                            if (bodyCart.colorId == null)
                                root.showSnackBar(getString(R.string.selectTheOneOfColors))
                            else
                                cartViewModel.addToCart(productId, bodyCart)
                        } else {
                            cartViewModel.addToCart(productId, bodyCart)
                        }
                    } else {
                        root.showSnackBar(getString(R.string.shouldExistsProductInStore))
                    }
                }
            }
        }
    }

    private fun updateAddToCartUI(btn: MaterialButton) {
        btn.apply {
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.royalBlue))
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.cart_circle_check)
            text = getString(R.string.existsInCart)
        }
    }


    //--- life cycle ---//
    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        if (this::countDownTimer.isInitialized) countDownTimer.cancel()
        _binding = null
    }

}