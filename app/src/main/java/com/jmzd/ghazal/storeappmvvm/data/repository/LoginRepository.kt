package com.jmzd.ghazal.storeappmvvm.data.repository

import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.network.ApiServices
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api : ApiServices) {
    suspend fun postLogin(body: BodyLogin) = api.postLogin(body)
    suspend fun postVerify(body: BodyLogin) = api.postVerify(body)
}