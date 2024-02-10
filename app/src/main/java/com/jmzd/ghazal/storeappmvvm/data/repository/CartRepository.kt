package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.data.models.cart.BodyAddToCart
import javax.inject.Inject

class CartRepository @Inject constructor(private val api : ApiServices) {
    suspend fun addToCart(productId : Int, body : BodyAddToCart ) = api.addToCart(productId , body)
    suspend fun getCartList() = api.getCartList()
}