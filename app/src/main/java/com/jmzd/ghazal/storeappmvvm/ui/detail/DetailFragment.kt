package com.jmzd.ghazal.storeappmvvm.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentDetailBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.changeVisibility
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.DetailViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    //binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<DetailViewModel>()

    //args
    private val args by navArgs<DetailFragmentArgs>()

    //other
    private var productId = 0

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
//        PRODUCT_ID = data.id!!
//        initDetailHeaderView(data)
//        initDetailInfoView(data)
//        initDetailTimerView(data)
//        setupViewPager()
//        initDetailBottomView(data)
    }

    //--- life cycle ---//
    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}