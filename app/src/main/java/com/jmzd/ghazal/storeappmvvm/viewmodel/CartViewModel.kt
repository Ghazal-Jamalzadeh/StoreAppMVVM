package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.SimpleResponse
import com.jmzd.ghazal.storeappmvvm.data.models.cart.BodyAddToCart
import com.jmzd.ghazal.storeappmvvm.data.models.cart.ResponseCartList
import com.jmzd.ghazal.storeappmvvm.data.models.cart.ResponseUpdateCart
import com.jmzd.ghazal.storeappmvvm.data.repository.CartRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : ViewModel() {

    //detail
    private val _addToCartLiveData = MutableLiveData<NetworkRequest<SimpleResponse>>()
    val addToCartLiveData: LiveData<NetworkRequest<SimpleResponse>> = _addToCartLiveData
    //cart list
    private val _cartListLiveData = MutableLiveData<NetworkRequest<ResponseCartList>>()
    val cartListLiveData: LiveData<NetworkRequest<ResponseCartList>> = _cartListLiveData
    //update cart
    private val _updateCartLiveData = MutableLiveData<NetworkRequest<ResponseUpdateCart>>()
    val updateCartLiveData: LiveData<NetworkRequest<ResponseUpdateCart>> = _updateCartLiveData
    //continue
    private val _continueLiveData = MutableLiveData<NetworkRequest<ResponseCartList>>()
    val continueLiveData: LiveData<NetworkRequest<ResponseCartList>> = _continueLiveData

    //--- api call ---//
    fun addToCart(productId : Int , body : BodyAddToCart) = viewModelScope.launch {

        _addToCartLiveData.value = NetworkRequest.Loading()

        val response: Response<SimpleResponse> = repository.addToCart(productId , body)

        _addToCartLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun getCartList() = viewModelScope.launch {

        _cartListLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseCartList> = repository.getCartList()

        _cartListLiveData.value = ResponseHandler(response).generateResponse()
    }

    fun increment(id : Int) = viewModelScope.launch {

        _updateCartLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseUpdateCart> = repository.cartIncrement(id)

        _updateCartLiveData.value = ResponseHandler(response).generateResponse()
    }


    fun decrement(id : Int) = viewModelScope.launch {

        _updateCartLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseUpdateCart> = repository.cartDecrement(id)

        _updateCartLiveData.value = ResponseHandler(response).generateResponse()
    }


    fun deleteProduct(id : Int) = viewModelScope.launch {

        _updateCartLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseUpdateCart> = repository.cartDeleteProduct(id)

        _updateCartLiveData.value = ResponseHandler(response).generateResponse()
    }


    fun cartContinue() = viewModelScope.launch {

        _continueLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseCartList> = repository.cartContinue()

        _continueLiveData.value = ResponseHandler(response).generateResponse()
    }



}