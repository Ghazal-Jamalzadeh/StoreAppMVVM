package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount
import com.jmzd.ghazal.storeappmvvm.data.repository.HomeRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    init {
        viewModelScope.launch {
            delay(300)
            getBanners()
            getDiscounts()
        }
    }

    //banners
    private val _bannersLiveData = MutableLiveData<MyResponse<ResponseBanners>>()
    val bannersLiveData: LiveData<MyResponse<ResponseBanners>> = _bannersLiveData
    //discounts
    private val _discountsLiveData = MutableLiveData<MyResponse<ResponseDiscount>>()
    val discountsLiveData: LiveData<MyResponse<ResponseDiscount>> = _discountsLiveData

    private fun getBanners() = viewModelScope.launch {

        _bannersLiveData.value = MyResponse.Loading()

        val response: Response<ResponseBanners> = repository.getBanners()

        _bannersLiveData.value = ResponseHandler(response).generateResponse()
    }


    private fun getDiscounts() = viewModelScope.launch {

        _discountsLiveData.value = MyResponse.Loading()

        val response: Response<ResponseDiscount> = repository.getDiscounts()

        _discountsLiveData.value = ResponseHandler(response).generateResponse()
    }
}