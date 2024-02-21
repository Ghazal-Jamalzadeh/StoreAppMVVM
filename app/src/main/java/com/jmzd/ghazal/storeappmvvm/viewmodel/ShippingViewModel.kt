package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySetAddressForShipping
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseSetAddressForShipping
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.BodyCoupon
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.ResponseCoupon
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.ResponseShipping
import com.jmzd.ghazal.storeappmvvm.data.repository.ShippingRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ShippingViewModel @Inject constructor(private val repository: ShippingRepository) :
    ViewModel() {

    //shipping
    private val _shippingLiveData = MutableLiveData<NetworkRequest<ResponseShipping>>()
    val shippingLiveData: LiveData<NetworkRequest<ResponseShipping>> = _shippingLiveData
    //coupon
    private val _couponLiveData = MutableLiveData<NetworkRequest<ResponseCoupon>>()
    val couponLiveData: LiveData<NetworkRequest<ResponseCoupon>> = _couponLiveData

    //--- api call ---//
    fun getShipping() = viewModelScope.launch {

        _shippingLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseShipping> = repository.getShipping()

        _shippingLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun setAddress(body: BodySetAddressForShipping) = viewModelScope.launch {
       repository.setAddress(body)
    }

    fun checkCoupon(body : BodyCoupon) = viewModelScope.launch {

        _couponLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseCoupon> = repository.checkCoupon(body)

        _couponLiveData.value = ResponseHandler(response).generateResponse()
    }

}