package com.jmzd.ghazal.storeappmvvm.ui.profile_address_add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentAddressAddBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileAddressesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressAddFragment : BaseFragment() {

    //binding
    private var _binding: FragmentAddressAddBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<ProfileAddressesViewModel>()

    //province
    private val provincesNamesList = mutableListOf<String>()
    private lateinit var provincesAdapter: ArrayAdapter<String>
    private var provinceId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddressAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //call api
        if (isNetworkAvailable) {
            viewModel.getProvinceList()
        }
        //init views
        binding.apply {

        }

        //observers
        observeProvinceListLiveData()
    }

    private fun observeProvinceListLiveData() {
        binding.apply {
            viewModel.provinceListLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {}

                    is MyResponse.Success -> {
                        response.data?.let { data ->
                            if (data.isNotEmpty()) {
                                data.forEach {
                                    provincesNamesList.add(it.title!!)
                                }
                                provincesAdapter = ArrayAdapter<String>(
                                    requireContext(), R.layout.dropdown_menu_popup_item, provincesNamesList
                                )
                                provinceAutoTxt.apply {
                                    setAdapter(provincesAdapter)
                                    setOnItemClickListener { _, _, position, _ ->
                                        provinceId = data[position].id!!
//                                        body.provinceId = provinceId.toString()
//                                        if (isNetworkAvailable)
//                                            viewModel.callCitiesListApi(provinceId)
                                    }
                                }
                            }
                        }
                    }

                    is MyResponse.Error -> {
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