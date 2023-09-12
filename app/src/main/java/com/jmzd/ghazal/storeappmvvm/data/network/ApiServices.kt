package com.jmzd.ghazal.storeappmvvm.data.network

import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices {

    @Headers("ACCEPT : APPLICATION/JSON")
    @POST("auth/login")
    suspend fun postLogin(@Body body : BodyLogin) : Response<ResponseLogin>
}