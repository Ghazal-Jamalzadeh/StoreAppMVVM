package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import com.jmzd.ghazal.storeappmvvm.data.models.cart.BodyAddToCart
import javax.inject.Inject

class CartRepository @Inject constructor(private val api : ApiServices) {
    suspend fun addToCart(productId : Int, body : BodyAddToCart ) = api.addToCart(productId , body)
    suspend fun getCartList() = api.getCartList()
    suspend fun cartIncrement(id : Int ) = api.cartIncrement(id)
    suspend fun cartDecrement(id : Int ) = api.cartDecrement(id)
    suspend fun cartDelete(id : Int ) = api.cartDelete(id)
}