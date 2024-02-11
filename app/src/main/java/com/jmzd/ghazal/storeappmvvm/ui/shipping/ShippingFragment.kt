package com.jmzd.ghazal.storeappmvvm.ui.shipping

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySetAddressForShipping
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.ResponseShipping
import com.jmzd.ghazal.storeappmvvm.databinding.DialogChangeAddressBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentSearchBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentShippingBinding
import com.jmzd.ghazal.storeappmvvm.ui.shipping.adapters.AddressesAdapter
import com.jmzd.ghazal.storeappmvvm.ui.shipping.adapters.ShippingAdapter
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.*
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.viewmodel.LoginViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.ShippingViewModel
import com.jmzd.ghazal.storeappmvvm.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShippingFragment : BaseFragment() {

    //binding
    private var _binding: FragmentShippingBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ShippingViewModel>()
    private val walletViewModel by viewModels<WalletViewModel>()

    //adapters
    @Inject
    lateinit var shippingAdapter : ShippingAdapter

    @Inject
    lateinit var addressesAdapter: AddressesAdapter

    //body
    @Inject
    lateinit var bodySetAddress : BodySetAddressForShipping

    //other
    private var finalPrice = 0

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
            walletViewModel.getWalletBalance()
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

        //observers
        observeShippingLiveData()
        obsereveWalletBalance()
    }

    //--- observers ---//
    private fun observeShippingLiveData() {
        binding.apply {
            viewModel.shippingLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        loading.changeVisibility(true, containerGroup)
                    }

                    is NetworkRequest.Success -> {
                        loading.changeVisibility(false, containerGroup)
                        response.data?.let { data ->
                            initShippingViews(data)
                        }
                    }

                    is NetworkRequest.Error -> {
                        loading.changeVisibility(false, containerGroup)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }


    private fun obsereveWalletBalance() {
        binding.apply {
            walletViewModel.walletBalanceLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        walletLoading.changeVisibility(true, walletTxt)
                    }

                    is NetworkRequest.Success -> {
                        walletLoading.changeVisibility(false, walletTxt)
                        response.data?.let { data ->
                            walletTxt.text = data.wallet.toString().toInt().moneySeparating()
                        }
                    }

                    is NetworkRequest.Error -> {
                        walletLoading.changeVisibility(false, walletTxt)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- init views ---//
    @SuppressLint("SetTextI18n")
    private fun initShippingViews(data: ResponseShipping) {
        binding.apply {
            //Order
            data.order?.let { order ->
//                finalPrice = order.finalPrice.toString().toInt()
                invoiceTitle.text = order.finalPrice.toString().toInt().moneySeparating()
                //Orders list
                initRecycler(order.orderItems)
            }
            //Addresses
            if (data.addresses.isNullOrEmpty().not()) {
                data.addresses?.get(0)?.let { address ->
                    setAddressData(address)
                }
                //More address
                if (data.addresses!!.size > 1) {
                    shippingAddressLay.changeAddressTxt.apply {
                        isVisible = true
                        setOnClickListener {
                            showChangeAddressDialog(data.addresses)
                        }
                    }
                }
            }
            //Payment
//            submitBtn.setOnClickListener {
//                if (data.addresses.isNullOrEmpty().not()) {
//                    if (isNetworkAvailable)
//                        viewModel.callPaymentApi(bodyCoupon)
//                } else {
//                    root.showSnackBar(getString(R.string.addressCanNotBeEmpty))
//                }
//            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAddressData(address: ResponseShipping.Addresse) {
        //Body
        bodySetAddress.addressId = address.id.toString()
        //Set data
        binding.shippingAddressLay.apply {
            recipientNameTxt.text = "${address.receiverFirstname} ${address.receiverLastname}"
            locationTxt.text = address.postalAddress
            phoneTxt.text = address.receiverCellphone
        }
        //Call set address
        if (isNetworkAvailable)
            viewModel.setAddress(bodySetAddress)
    }

    //--- recyclers ---//
    private fun initRecycler(list: List<ResponseShipping.Order.OrderItem>) {
        shippingAdapter.setData(list)
        binding.productsList.setupRecyclerview(LinearLayoutManager(requireContext()), shippingAdapter)
    }

    //--- dialogs ---//
    private fun showChangeAddressDialog(list: List<ResponseShipping.Addresse>) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogChangeAddressBinding.inflate(layoutInflater)
        dialog.transparentCorners()
        dialog.setContentView(dialogBinding.root)
        addressesAdapter.setData(list)
        dialogBinding.addressList.setupRecyclerview(LinearLayoutManager(requireContext()), addressesAdapter)
        //Click
        addressesAdapter.setOnItemCLickListener { address ->
            setAddressData(address)
            //Dismiss
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}