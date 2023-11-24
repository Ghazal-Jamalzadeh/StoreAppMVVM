package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.categories.ResponseCategories
import com.jmzd.ghazal.storeappmvvm.data.repository.CategoriesRepository
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repository: CategoriesRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(300)
            getCategories()
        }
    }


    //banners
    private val _categoriesLiveData = MutableLiveData<MyResponse<ResponseCategories>>()
    val categoriesLiveData: LiveData<MyResponse<ResponseCategories>> = _categoriesLiveData

    //--- api call ---//
    private fun getCategories() = viewModelScope.launch {

        _categoriesLiveData.value = MyResponse.Loading()

        val response: Response<ResponseCategories> = repository.getCategories()

        _categoriesLiveData.value = ResponseHandler(response).generateResponse()
    }



}