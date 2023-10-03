package com.jmzd.ghazal.storeappmvvm.viewmodel

import android.os.Parcelable
import androidx.lifecycle.*
import androidx.navigation.NavType
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseProducts
import com.jmzd.ghazal.storeappmvvm.data.repository.HomeRepository
import com.jmzd.ghazal.storeappmvvm.utils.NEW
import com.jmzd.ghazal.storeappmvvm.utils.SORT
import com.jmzd.ghazal.storeappmvvm.utils.enums.ProductsCategories
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

    //save state
    var lastScrollState : Parcelable? = null

    //banners
    private val _bannersLiveData = MutableLiveData<MyResponse<ResponseBanners>>()
    val bannersLiveData: LiveData<MyResponse<ResponseBanners>> = _bannersLiveData
    //discounts
    private val _discountsLiveData = MutableLiveData<MyResponse<ResponseDiscount>>()
    val discountsLiveData: LiveData<MyResponse<ResponseDiscount>> = _discountsLiveData

    //--- api call ---//
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

    //--- Products ---//
    private fun getProductsQueries(): Map<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[SORT] = NEW
        return queries
    }
    private val categoriesName : Map<ProductsCategories , LiveData<MyResponse<ResponseProducts>>>
    = ProductsCategories.values().associateWith {
        getProducts(it)
    }

    private fun getProducts(category : ProductsCategories) : LiveData<MyResponse<ResponseProducts>>
    = liveData {

        val cats : String = category.label

        emit(MyResponse.Loading())

        val response: Response<ResponseProducts> = repository.getProducts(cats , getProductsQueries())

        emit(ResponseHandler(response).generateResponse())

    }

    fun getProductsLiveData(category: ProductsCategories) : LiveData<MyResponse<ResponseProducts>>
    = categoriesName.getValue(category)
}
