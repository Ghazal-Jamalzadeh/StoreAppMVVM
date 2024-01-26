package com.jmzd.ghazal.storeappmvvm.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.databinding.DialogImageBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentDetailBinding
import com.jmzd.ghazal.storeappmvvm.ui.detail.adapters.ImagesAdapter
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.constants.BASE_URL_IMAGE
import com.jmzd.ghazal.storeappmvvm.utils.constants.COLOR_BLACK
import com.jmzd.ghazal.storeappmvvm.utils.constants.COLOR_WHITE
import com.jmzd.ghazal.storeappmvvm.utils.extensions.*
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    //binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<DetailViewModel>()

    //args
    private val args by navArgs<DetailFragmentArgs>()

    //adapter
    @Inject
    lateinit var imagesAdapter: ImagesAdapter

    //other
    private var productId = 0
    private var isNeededToColor = false

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

    //--- init views ---//
    private fun initDetailViews(data: ResponseDetail) {
        loadImage(data.image!!)
//        PRODUCT_ID = data.id!!
        initDetailHeaderView(data)
//        initDetailInfoView(data)
//        initDetailTimerView(data)
//        setupViewPager()
//        initDetailBottomView(data)
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
//                    viewModel.callProductLike(productId)
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
//                    if (isChecked)
//                        bodyCart.colorId = chip.id.toString()
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

    //--- life cycle ---//
    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}