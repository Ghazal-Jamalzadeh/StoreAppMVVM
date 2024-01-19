package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySubmitAddress
import javax.inject.Inject

class AddressesRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getProfileAddresses() = api.getProfileAddresses()
    suspend fun getProvinceList() = api.getProvinceList()
    suspend fun getCityList(provinceId : Int) = api.getCityList(provinceId)
    suspend fun submitAddress(body : BodySubmitAddress) = api.submitAddress(body)
    suspend fun removeAddress(addressId : Int ) = api.removeAddress(addressId)
}