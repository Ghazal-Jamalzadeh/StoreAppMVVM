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
import com.jmzd.ghazal.storeappmvvm.data.models.profile_order.ResponseProfileOrdersList
import com.jmzd.ghazal.storeappmvvm.data.repository.AddressesRepository
import com.jmzd.ghazal.storeappmvvm.data.repository.OrdersRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileOrdersViewModel @Inject constructor(private val repository: OrdersRepository) : ViewModel() {

    //profile addresses
    private val _profileOrdersLiveData = MutableLiveData<MyResponse<ResponseProfileOrdersList>>()
    val profileOrdersLiveData: LiveData<MyResponse<ResponseProfileOrdersList>> = _profileOrdersLiveData


    //--- api call ---//
    fun getOrdersList(status : String) = viewModelScope.launch {

        _profileOrdersLiveData.value = MyResponse.Loading()

        val response: Response<ResponseProfileOrdersList> = repository.getOrdersList(status)

        _profileOrdersLiveData.value = ResponseHandler(response).generateResponse()
    }

}