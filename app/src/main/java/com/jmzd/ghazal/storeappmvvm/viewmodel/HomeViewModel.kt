package com.jmzd.ghazal.storeappmvvm.viewmodel

import android.os.Parcelable
import androidx.lifecycle.*
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseProducts
import com.jmzd.ghazal.storeappmvvm.data.repository.HomeRepository
import com.jmzd.ghazal.storeappmvvm.utils.constants.NEW
import com.jmzd.ghazal.storeappmvvm.utils.constants.SORT
import com.jmzd.ghazal.storeappmvvm.utils.enums.ProductsCategories
import com.jmzd.ghazal.storeappmvvm.utils.network.NetworkRequest
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

    //save state
    var lastScrollState : Parcelable? = null

    //banners
    private val _bannersLiveData = MutableLiveData<NetworkRequest<ResponseBanners>>()
    val bannersLiveData: LiveData<NetworkRequest<ResponseBanners>> = _bannersLiveData
    //discounts
    private val _discountsLiveData = MutableLiveData<NetworkRequest<ResponseDiscount>>()
    val discountsLiveData: LiveData<NetworkRequest<ResponseDiscount>> = _discountsLiveData

    //--- api call ---//
    private fun getBanners() = viewModelScope.launch {

        _bannersLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseBanners> = repository.getBanners()

        _bannersLiveData.value = ResponseHandler(response).generateResponse()
    }


    private fun getDiscounts() = viewModelScope.launch {

        _discountsLiveData.value = NetworkRequest.Loading()

        val response: Response<ResponseDiscount> = repository.getDiscounts()

        _discountsLiveData.value = ResponseHandler(response).generateResponse()
    }

    //--- Products ---//
    private fun getProductsQueries(): Map<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[SORT] = NEW
        return queries
    }
    private val categoriesName : Map<ProductsCategories , LiveData<NetworkRequest<ResponseProducts>>>
    = ProductsCategories.values().associateWith {
        getProducts(it)
    }

    private fun getProducts(category : ProductsCategories) : LiveData<NetworkRequest<ResponseProducts>>
    = liveData {

        val cats : String = category.label

        emit(NetworkRequest.Loading())

        val response: Response<ResponseProducts> = repository.getProducts(cats , getProductsQueries())

        emit(ResponseHandler(response).generateResponse())

    }

    fun getProductsLiveData(category: ProductsCategories) : LiveData<NetworkRequest<ResponseProducts>>
    = categoriesName.getValue(category)
}
