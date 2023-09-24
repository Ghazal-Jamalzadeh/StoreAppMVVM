package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.network.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getBanners() = api.getBanners(value = "general")
}