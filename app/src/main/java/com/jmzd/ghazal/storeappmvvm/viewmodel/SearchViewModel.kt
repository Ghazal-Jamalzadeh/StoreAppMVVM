package com.jmzd.ghazal.storeappmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch
import com.jmzd.ghazal.storeappmvvm.data.models.search_filter.FilterModel
import com.jmzd.ghazal.storeappmvvm.data.repository.SearchFilterRepository
import com.jmzd.ghazal.storeappmvvm.data.repository.SearchRepository
import com.jmzd.ghazal.storeappmvvm.utils.SORT
import com.jmzd.ghazal.storeappmvvm.utils.network.MyResponse
import com.jmzd.ghazal.storeappmvvm.utils.network.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository ,
    private val searchFilterRepository: SearchFilterRepository
    ) : ViewModel(){

    //search
    private val _searchLiveData = MutableLiveData<MyResponse<ResponseSearch>>()
    val searchLiveData: LiveData<MyResponse<ResponseSearch>> = _searchLiveData
    //search filter
    private val _filterLiveData = MutableLiveData<MutableList<FilterModel>>()
    val filterLiveData: LiveData<MutableList<FilterModel>> = _filterLiveData
    //selected filter
    private val _selectedFilterLiveData = MutableLiveData<String>()
    val selectedFilterLiveData: LiveData<String> = _selectedFilterLiveData


    //--- search ---//
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

    //--- search filter ---//
    fun getFilters() = viewModelScope.launch {
        _filterLiveData.value = searchFilterRepository.getFilterData()
    }

    //--- select filters ---//
    fun sendSelectedFilter(item : String){
        _selectedFilterLiveData.value = item
    }
}