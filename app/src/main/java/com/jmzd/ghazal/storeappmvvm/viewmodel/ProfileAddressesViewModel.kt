package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProfileAddresses
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProvinceList
import com.jmzd.ghazal.storeappmvvm.data.repository.AddressesRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileAddressesViewModel @Inject constructor(private val repository: AddressesRepository) : ViewModel() {

    //profile addresses
    private val _profileAddressesLiveData = MutableLiveData<MyResponse<ResponseProfileAddresses>>()
    val profileAddressesLiveData: LiveData<MyResponse<ResponseProfileAddresses>> = _profileAddressesLiveData

    //province list
    private val _provinceListLiveData = MutableLiveData<MyResponse<ResponseProvinceList>>()
    val provinceListLiveData: LiveData<MyResponse<ResponseProvinceList>> = _provinceListLiveData

    //--- api call ---//
    fun getProfileAddresses() = viewModelScope.launch {

        _profileAddressesLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProfileAddresses> = repository.getProfileAddresses()

        _profileAddressesLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun getProvinceList() = viewModelScope.launch {

        _provinceListLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProvinceList> = repository.getProvinceList()

        _provinceListLiveData.value = ResponseHandler(response).generateResponse()
    }



}