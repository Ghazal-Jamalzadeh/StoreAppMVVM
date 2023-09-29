package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import javax.inject.Inject

class PofileRepository  @Inject constructor(private val api : ApiServices) {
    suspend fun getProfile() = api.getProfile()
}