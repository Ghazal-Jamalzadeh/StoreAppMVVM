package com.jmzd.ghazal.storeappmvvm.ui.profile_address_add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySubmitAddress
import com.jmzd.ghazal.storeappmvvm.databinding.DialogDeleteAddressBinding
import com.jmzd.ghazal.storeappmvvm.databinding.FragmentAddressAddBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseFragment
import com.jmzd.ghazal.storeappmvvm.utils.events.EventBus
import com.jmzd.ghazal.storeappmvvm.utils.events.Events
import com.jmzd.ghazal.storeappmvvm.utils.extensions.enableLoading
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setTint
import com.jmzd.ghazal.storeappmvvm.utils.extensions.showSnackBar
import com.jmzd.ghazal.storeappmvvm.utils.extensions.transparentCorners
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.viewmodel.ProfileAddressesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    //city
    private val citiesNamesList = mutableListOf<String>()
    private lateinit var citiesAdapter: ArrayAdapter<String>

    //address
    private var addressId = 0

    //body
    @Inject
    lateinit var body : BodySubmitAddress

    //nav args
    private val args by navArgs<AddressAddFragmentArgs>()


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
            toolbar.apply {
                //Args
                if (args.addressData != null) {
                    toolbarTitleTxt.text = getString(R.string.editAddress)
                    toolbarOptionImg.apply {
                        setImageResource(R.drawable.trash_can)
                        setTint(R.color.red)
                        setOnClickListener {
                            showDeleteAddressDialog()
                        }
                    }
                    //Set data
                    args.addressData?.apply {
                        addressId = id!!
                        body.addressId = id.toString()
                        nameEdt.setText(receiverFirstname)
                        familyEdt.setText(receiverLastname)
                        phoneEdt.setText(receiverCellphone)
                        body.provinceId = province?.id.toString()
                        provinceAutoTxt.setText(province?.title)
                        cityInpLay.isVisible = true
                        body.cityId = cityId
                        cityAutoTxt.setText(city?.title)
                        floorEdt.setText(floor)
                        plateEdt.setText(plateNumber)
                        postalEdt.setText(postalCode)
                        addressEdt.setText(postalAddress)
                    }
                } else {
                    toolbarTitleTxt.text = getString(R.string.addNewAddress)
                    toolbarOptionImg.isVisible = false
                }
                //Back
                toolbarBackImg.setOnClickListener { findNavController().popBackStack() }
            }

            //Submit
            submitBtn.setOnClickListener {
                body.receiverFirstname = nameEdt.text.toString()
                body.receiverLastname = familyEdt.text.toString()
                body.receiverCellphone = phoneEdt.text.toString()
                body.floor = floorEdt.text.toString()
                body.plateNumber = plateEdt.text.toString()
                body.postalCode = postalEdt.text.toString()
                body.postalAddress = addressEdt.text.toString()
                //Call api
                if (isNetworkAvailable)
                    viewModel.submitAddress(body)
            }
        }

        //observers
        observeProvinceListLiveData()
        observeCityListLiveData()
        observeSubmitAddressLiveData()
    }

    //--- observers ---//
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
                                        body.provinceId = provinceId.toString()
                                        if (isNetworkAvailable)
                                            viewModel.getCityList(provinceId)
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

    private fun observeCityListLiveData() {
        binding.apply {
            viewModel.cityListLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {}

                    is MyResponse.Success -> {
                        response.data?.let { data ->
                            cityInpLay.isVisible = true
                            if (data.isNotEmpty()) {
                                citiesNamesList.clear()
                                data.forEach {
                                    citiesNamesList.add(it.title!!)
                                }
                                citiesAdapter = ArrayAdapter<String>(
                                    requireContext(), R.layout.dropdown_menu_popup_item, citiesNamesList
                                )
                                cityAutoTxt.apply {
                                    setAdapter(citiesAdapter)
                                    setOnItemClickListener { _, _, position, _ ->
                                        body.cityId = data[position].id!!.toString()
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

    private fun observeSubmitAddressLiveData() {
        binding.apply {
            viewModel.submitAddressLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Loading -> {
                        submitBtn.enableLoading(true)
                    }

                    is MyResponse.Success -> {
                        submitBtn.enableLoading(false)
                        response.data?.let {
                            lifecycleScope.launch { EventBus.publish(Events.IsUpdateAddress) }
                            findNavController().popBackStack()
                        }
                    }
                    is MyResponse.Error -> {
                        submitBtn.enableLoading(false)
                        root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    //--- Dialog ---//
    private fun showDeleteAddressDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogDeleteAddressBinding.inflate(layoutInflater)
        dialog.transparentCorners()
        dialog.setContentView(dialogBinding.root)

        dialogBinding.noBtn.setOnClickListener { dialog.dismiss() }

        dialogBinding.yesBtn.setOnClickListener {
            dialog.dismiss()
            if (isNetworkAvailable)
                viewModel.removeAddress(addressId)
        }

        dialog.show()
    }


    //--- life cycle ---//
    override fun onNetworkLost() {
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}