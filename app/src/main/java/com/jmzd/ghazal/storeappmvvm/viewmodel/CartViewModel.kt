package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.SimpleResponse
import com.jmzd.ghazal.storeappmvvm.data.models.cart.BodyAddToCart
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.data.repository.CartRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : ViewModel() {

    //detail
    private val _addToCartLiveData = MutableLiveData<MyResponse<SimpleResponse>>()
    val addToCartLiveData: LiveData<MyResponse<SimpleResponse>> = _addToCartLiveData


    //--- api call ---//
    fun addToCart(productId : Int , body : BodyAddToCart) = viewModelScope.launch {

        _addToCartLiveData.value = MyResponse.Loading()

        val response: Response<SimpleResponse> = repository.addToCart(productId , body)

        _addToCartLiveData.value = ResponseHandler(response).generateResponse()
    }

}