package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.utils.constants.ELECTRONIC_DEVICES
import com.jmzd.ghazal.storeappmvvm.utils.constants.GENERAL
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getBanners() = api.getBanners(value = GENERAL)
    suspend fun getDiscounts() = api.getDiscounts(value = ELECTRONIC_DEVICES)
    suspend fun getProducts(slug : String , queries : Map<String , String>) = api.getProducts(value = slug , map =  queries)
}