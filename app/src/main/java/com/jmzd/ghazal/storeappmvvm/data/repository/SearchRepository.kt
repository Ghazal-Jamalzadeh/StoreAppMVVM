package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api : ApiServices) {
    suspend fun search(queries : Map<String , String>) = api.getSearch(map =  queries)
}