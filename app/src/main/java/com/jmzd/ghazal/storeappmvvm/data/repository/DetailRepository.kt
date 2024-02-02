package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.api.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getDetail(productId : Int) = api.getDetail(productId)
    suspend fun postLikeProduct(productId : Int) = api.postLikeProduct(productId)
    suspend fun getDetailFeatures(productId : Int) = api.getDetailFeatures(productId)
}