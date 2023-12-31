package com.jmzd.ghazal.storeappmvvm.data.api

import com.jmzd.ghazal.storeappmvvm.data.models.categories.ResponseCategories
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseDiscount
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseProducts
import com.jmzd.ghazal.storeappmvvm.data.models.login.BodyLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseLogin
import com.jmzd.ghazal.storeappmvvm.data.models.login.ResponseVerify
import com.jmzd.ghazal.storeappmvvm.data.models.profile.BodyUpdateProfile
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseProfile
import com.jmzd.ghazal.storeappmvvm.data.models.profile.ResponseWallet
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseDeleteComment
import com.jmzd.ghazal.storeappmvvm.data.models.profile_comments.ResponseProfileComments
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProfileFavorites
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.BodyIncreaseWallet
import com.jmzd.ghazal.storeappmvvm.data.models.wallet.ResponseIncreaseWallet
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    @POST("auth/login")
    suspend fun postLogin(@Body body : BodyLogin) : Response<ResponseLogin>

    @POST("auth/login/verify")
    suspend fun postVerify(@Body body: BodyLogin) : Response<ResponseVerify>

    @GET("auth/detail")
    suspend fun getProfile() : Response<ResponseProfile>

    @GET("ad/swiper/{slug}")
    suspend fun getBanners(@Path("slug") value : String) : Response<ResponseBanners>

    @GET("offers/discount/{slug}")
    suspend fun getDiscounts(@Path("slug") value : String) : Response<ResponseDiscount>

    @GET("category/pro/{slug}")
    suspend fun getProducts(@Path("slug") value: String , @QueryMap map : Map<String , String>): Response<ResponseProducts>

    @GET("search")
    suspend fun getSearch(@QueryMap map : Map<String , String>) : Response<ResponseSearch>

    @GET("menu")
    suspend fun getCategories() : Response<ResponseCategories>

    @GET("auth/wallet")
    suspend fun getWalletBalance() : Response<ResponseWallet>

    @POST("auth/wallet")
    suspend fun increaseWallet(@Body body : BodyIncreaseWallet) : Response<ResponseIncreaseWallet>

    @POST("auth/avatar")
    suspend fun uploadAvatar(@Body body : RequestBody) : Response<Unit>

    @PUT("auth/update")
    suspend fun updateProfile(@Body body : BodyUpdateProfile) : Response<ResponseProfile>

    @GET("auth/comments")
    suspend fun getProfileComments() : Response<ResponseProfileComments>

    @DELETE("auth/comment/{comment_id}")
    suspend fun deleteComment(@Path("comment_id") commentId :Int) : Response<ResponseDeleteComment>

    @GET("auth/favorites")
    suspend fun getFavorites() : Response<ResponseProfileFavorites>

    @DELETE("auth/favorites/{id}")
    suspend fun deleteFavorite(@Path("id") id :Int) : Response<ResponseDeleteComment>

}