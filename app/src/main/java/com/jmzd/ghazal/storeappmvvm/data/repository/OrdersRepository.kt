package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import javax.inject.Inject

class OrdersRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getOrdersList(status : String) = api.getOrdersList(status)
}