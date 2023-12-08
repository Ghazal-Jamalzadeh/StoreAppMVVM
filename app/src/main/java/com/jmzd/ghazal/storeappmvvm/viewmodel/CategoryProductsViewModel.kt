package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseProducts
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch
import com.jmzd.ghazal.storeappmvvm.data.repository.CategoryProductsRepository
import com.jmzd.ghazal.storeappmvvm.utils.*
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(private val repository: CategoryProductsRepository) :
    ViewModel() {

    //products live data
    private val _productsLiveData = MutableLiveData<MyResponse<ResponseProducts>>()
    val productsLiveData: LiveData<MyResponse<ResponseProducts>> = _productsLiveData

    //queries
    fun getProductsQueries(
        sort: String? = null,
        search: String? = null,
        minPrice: String? = null,
        maxPrice: String? = null,
        brands: String? = null,
        isAvailable: Boolean? = null,
    ): Map<String, String> {
        val queries: HashMap<String, String> = HashMap()

        sort?.let {
            queries[SORT] = it
        }

        search?.let {
            queries[SEARCH] = it
        }

        minPrice?.let {
            queries[MIN_PRICE] = it
        }
        maxPrice?.let {
            queries[MAX_PRICE] = it
        }
        brands?.let {
            queries[SELECTED_BRANDS] = it
        }
        isAvailable?.let {
            queries[ONLY_AVAILABLE] = it.toString()
        }

        return queries
    }

    //call api
    fun getProductsByCategory(slug: String, queries: Map<String, String>) =
        viewModelScope.launch {

            _productsLiveData.value = MyResponse.Loading()

            val response: Response<ResponseProducts> = repository.getProducts(slug, queries)

            _productsLiveData.value = ResponseHandler(response).generateResponse()
        }
}