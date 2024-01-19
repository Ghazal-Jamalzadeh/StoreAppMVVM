package com.jmzd.ghazal.storeappmvvm.ui.profile_address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProfileAddresses
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProfileAddresses.ResponseProfileAddressesItem
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentProfileAddressBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.events.EventBus
import com.jmzd.ghazal.storeappmvvm.utils.events.Events
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileAddressesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileAddressFragment : BaseFragment() {

    //binding
    private var _binding: FragmentProfileAddressBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileAddressesViewModel>()


    @Inject
    lateinit var addressesAdapter: AddressesAdapter

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
                        findNavController().navigate(R.id.action_profileAddressFragment_to_addressAddFragment)
                    }
                }
            }
        }

        //observers
        observeProfileAddressesLiveData()

        //Auto update profile
        lifecycleScope.launch {
            EventBus.subscribe<Events.IsUpdateAddress> {
                if (isNetworkAvailable)
                    viewModel.getProfileAddresses()
            }
        }
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
                                initRecycler(data)
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



    private fun initRecycler(data: List<ResponseProfileAddressesItem>) {
        binding.apply {
            addressesAdapter.setData(data)
            addressList.setupRecyclerview(LinearLayoutManager(requireContext()), addressesAdapter)
            //Click
            addressesAdapter.setOnItemClickListener {
                val direction = ProfileAddressFragmentDirections.actionProfileAddressFragmentToAddressAddFragment().setAddressData(it)
                findNavController().navigate(direction)
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