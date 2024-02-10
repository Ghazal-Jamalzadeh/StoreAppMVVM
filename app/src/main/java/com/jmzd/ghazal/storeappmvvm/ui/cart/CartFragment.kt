package com.jmzd.ghazal.storeappmvvm.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentCartBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginPhoneBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentLoginVerifyBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.CartViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    //binding
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call api
        if(isNetworkAvailable){
            viewModel.getCartList()
        }

        //init views
        binding.apply {

        }

        //observers
        observeCartListLiveData()
    }

    //--- observers ---//

    @SuppressLint("SetTextI18n")
    private fun observeCartListLiveData() {
        binding.apply {
            viewModel.cartListLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        loading.isVisible = true
                    }

                    is MyResponse.Success -> {
                        loading.isVisible = false
                        response.data?.let { data ->
                            if (data.orderItems != null) {
                                if (data.orderItems.isNotEmpty()) {
                                    emptyLay.isVisible = false
                                    cartsList.isVisible = true
                                    continueFABtn.isVisible = true
                                    //Continue
                                    continueFABtn.setOnClickListener {
//                                        isNavigateToShipping = true
//                                        if (isNetworkAvailable)
//                                            viewModel.callCartContinueApi()
                                    }
                                    //Toolbar txt
                                    toolbarPriceTxt.text = data.itemsPrice.toString().toInt().moneySeparating()
//                                    initRecyclerView(data.orderItems)
                                } else {
                                    emptyLay.isVisible = true
                                    cartsList.isVisible = false
                                    continueFABtn.isVisible = false
                                    toolbarPriceTxt.text = "0 ${getString(R.string.toman)}"
                                }
                            } else {
                                emptyLay.isVisible = true
                                cartsList.isVisible = false
                                continueFABtn.isVisible = false
                                toolbarPriceTxt.text = "0 ${getString(R.string.toman)}"
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        loading.isVisible = false
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }


    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}