package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySubmitAddress
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProfileAddresses
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProvinceList
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseSubmitAddress
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseDeleteComment
import com.jmzd.ghazal.storeappmvvm.data.repository.AddressesRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileAddressesViewModel @Inject constructor(private val repository: AddressesRepository) : ViewModel() {

    //profile addresses
    private val _profileAddressesLiveData = MutableLiveData<NetworkRequest<ResponseProfileAddresses>>()
    val profileAddressesLiveData: LiveData<NetworkRequest<ResponseProfileAddresses>> = _profileAddressesLiveData

    //province list
    private val _provinceListLiveData = MutableLiveData<NetworkRequest<ResponseProvinceList>>()
    val provinceListLiveData: LiveData<NetworkRequest<ResponseProvinceList>> = _provinceListLiveData

    //city list
    private val _cityListLiveData = MutableLiveData<NetworkRequest<ResponseProvinceList>>()
    val cityListLiveData: LiveData<NetworkRequest<ResponseProvinceList>> = _cityListLiveData

    //submit address
    private val _submitAddressLiveData = MutableLiveData<NetworkRequest<ResponseSubmitAddress>>()
    val submitAddressLiveData: LiveData<NetworkRequest<ResponseSubmitAddress>> = _submitAddressLiveData

    //remove address
    private val _removeAddressLiveData = MutableLiveData<NetworkRequest<ResponseDeleteComment>>()
    val removeAddressLiveData: LiveData<NetworkRequest<ResponseDeleteComment>> = _removeAddressLiveData


    //--- api call ---//
    fun getProfileAddresses() = viewModelScope.launch {

        _profileAddressesLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProfileAddresses> = repository.getProfileAddresses()

        _profileAddressesLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun getProvinceList() = viewModelScope.launch {

        _provinceListLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProvinceList> = repository.getProvinceList()

        _provinceListLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun getCityList(provinceId : Int) = viewModelScope.launch {

        _cityListLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProvinceList> = repository.getCityList(provinceId)

        _cityListLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun submitAddress(body : BodySubmitAddress) = viewModelScope.launch {

        _submitAddressLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseSubmitAddress> = repository.submitAddress(body)

        _submitAddressLiveData.value = ResponseHandler(response).generateResponse()
    }


    fun removeAddress(addressId : Int) = viewModelScope.launch {

        _removeAddressLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseDeleteComment> = repository.removeAddress(addressId)

        _removeAddressLiveData.value = ResponseHandler(response).generateResponse()
    }





}