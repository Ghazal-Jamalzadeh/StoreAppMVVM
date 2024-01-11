package com.jmzd.ghazal.storeappmvvm.ui.profile_address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileAddressBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileAddressesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileAddressFragment : BaseFragment() {

    //binding
    private var _binding: FragmentProfileAddressBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileAddressesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call api
        if(isNetworkAvailable){
            viewModel.getProfileAddresses()
        }

        //init views
        binding.apply {
            //Toolbar
            toolbar.apply {
                toolbarTitleTxt.text = getString(R.string.yourAddresses)
                toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
                //Add
                toolbarOptionImg.apply {
                    setImageResource(R.drawable.location_plus)
                    setOnClickListener {
//                        findNavController().navigate(R.id.actionProfileToAddressAdd)
                    }
                }
            }
        }

        //observers
        observeProfileAddressesLiveData()
    }

    private fun observeProfileAddressesLiveData() {
        binding.apply {
            viewModel.profileAddressesLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        addressList.showShimmer()
                    }

                    is MyResponse.Success -> {
                        addressList.hideShimmer()
                        response.data?.let { data ->
                            if (data.isNotEmpty()) {
                                //initRecycler(data)
                            } else {
                                emptyLay.isVisible = true
                                addressList.isVisible = false
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        addressList.hideShimmer()
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