package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import javax.inject.Inject

class ShippingRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getShipping() = api.getShipping()
}