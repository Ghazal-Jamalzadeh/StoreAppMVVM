package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.profile_order.ResponseProfileOrdersList
import com.jmzd.ghazal.storeappmvvm.data.repository.AddressesRepository
import com.jmzd.ghazal.storeappmvvm.data.repository.OrdersRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileOrdersViewModel @Inject constructor(private val repository: OrdersRepository) : ViewModel() {

    //profile addresses
    private val _profileOrdersLiveData = MutableLiveData<NetworkRequest<ResponseProfileOrdersList>>()
    val profileOrdersLiveData: LiveData<NetworkRequest<ResponseProfileOrdersList>> = _profileOrdersLiveData


    //--- api call ---//
    fun getOrdersList(status : String) = viewModelScope.launch {

        _profileOrdersLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseProfileOrdersList> = repository.getOrdersList(status)

        _profileOrdersLiveData.value = ResponseHandler(response).generateResponse()
    }

}