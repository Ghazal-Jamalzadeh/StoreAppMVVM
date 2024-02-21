package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.data.models.address.BodySetAddressForShipping
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.BodyCoupon
import javax.inject.Inject

class ShippingRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getShipping() = api.getShipping()
    suspend fun setAddress(body : BodySetAddressForShipping) = api.shippingSetAddress(body)
    suspend fun checkCoupon(body : BodyCoupon) = api.checkCoupon(body)
    suspend fun payment(body : BodyCoupon) = api.payment(body)
}