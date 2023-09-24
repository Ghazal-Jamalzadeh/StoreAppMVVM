package com.jmzd.ghazal.storeappmvvm.data.network

import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseVerify
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    @POST("auth/login")
    suspend fun postLogin(@Body body : BodyLogin) : Response<ResponseLogin>

    @POST("auth/login/verify")
    suspend fun postVerify(@Body body: BodyLogin) : Response<ResponseVerify>

    @GET("auth/detail")
    suspend fun getProfile() : Response<ResponseProfile>

    @GET("ad/swiper/{slug}")
    suspend fun getBanners(@Path("slug") value : String) : Response<ResponseBanners>
}