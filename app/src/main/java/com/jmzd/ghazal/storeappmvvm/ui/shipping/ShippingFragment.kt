package com.jmzd.ghazal.storeappmvvm.ui.shipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentSearchBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentShippingBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.ShippingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingFragment : BaseFragment() {

    //binding
    private var _binding: FragmentShippingBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ShippingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShippingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //api call
        if(isNetworkAvailable){
            viewModel.getShipping()
        }

        //init views
        binding.apply {
            //Toolbar
            toolbar.apply {
                toolbarTitleTxt.text = getString(R.string.invoiceWithDeliveryPrice)
                toolbarOptionImg.isVisible = false
                toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
            }
        }
    }

    //--- observers ---//
//    private fun loadShippingData() {
//        binding.apply {
//            viewModel.shippingLiveData.observe(viewLifecycleOwner) { response ->
//                when (response) {
//                    is NetworkRequest.Loading -> {
//                        loading.isVisible(true, containerGroup)
//                    }
//
//                    is NetworkRequest.Success -> {
//                        loading.isVisible(false, containerGroup)
//                        response.data?.let { data ->
//                            initShippingViews(data)
//                        }
//                    }
//
//                    is NetworkRequest.Error -> {
//                        loading.isVisible(false, containerGroup)
//                        root.showSnackBar(response.error!!)
//                    }
//                }
//            }
//        }
//    }

    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}