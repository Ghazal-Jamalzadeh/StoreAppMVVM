package com.jmzd.ghazal.storeappmvvm.data.network

import androidx.datastore.preferences.protobuf.BoolValue
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseVerify
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices {
    @POST("auth/login")
    suspend fun postLogin(@Body body : BodyLogin) : Response<ResponseLogin>

    @POST("auth/login/verify")
    suspend fun postVerify(@Body body: BodyLogin) : Response<ResponseVerify>
}