package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch
import com.jmzd.ghazal.storeappmvvm.data.repository.SearchRepository
import com.jmzd.ghazal.storeappmvvm.utils.SORT
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel(){

    private val _searchLiveData = MutableLiveData<MyResponse<ResponseSearch>>()
    val searchLiveData: LiveData<MyResponse<ResponseSearch>> = _searchLiveData

    fun getSearchQueries(search : String , sort : String): Map<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[com.jmzd.ghazal.storeappmvvm.utils.Q] = search
        queries[SORT] = sort
        return queries
    }

    fun search(queries : Map<String , String>) = viewModelScope.launch {

        _searchLiveData.value = MyResponse.Loading()

        val response: Response<ResponseSearch> = repository.search(queries)

        _searchLiveData.value = ResponseHandler(response).generateResponse()
    }
}