package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.network.ApiServices
import com.jmzd.ghazal.storeappmvvm.utils.ELECTRONIC_DEVICES
import com.jmzd.ghazal.storeappmvvm.utils.GENERAL
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getBanners() = api.getBanners(value = GENERAL)
    suspend fun getDiscounts() = api.getDiscounts(value = ELECTRONIC_DEVICES)
}