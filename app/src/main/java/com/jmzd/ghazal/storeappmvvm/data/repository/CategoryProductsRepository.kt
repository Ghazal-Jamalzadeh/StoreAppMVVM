package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import javax.inject.Inject

class CategoryProductsRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getProducts(slug : String , queries : Map<String , String>) = api.getProducts(slug , queries)
}