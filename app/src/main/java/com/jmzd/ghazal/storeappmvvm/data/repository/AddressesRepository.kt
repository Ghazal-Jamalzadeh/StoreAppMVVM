package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import javax.inject.Inject

class AddressesRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getProfileAddresses() = api.getProfileAddresses()
    suspend fun getProvinceList() = api.getProvinceList()
    suspend fun getCityList(provinceId : Int) = api.getCityList(provinceId)
}