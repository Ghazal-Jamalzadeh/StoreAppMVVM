package com.jmzd.ghazal.storeappmvvm.data.api

import com.jmzd.ghazal.storeappmvvm.data.models.SimpleResponse
import com.jmzd.ghazal.storeappmvvm.data.models.address.*
import com.jmzd.ghazal.storeappmvvm.data.models.cart.BodyAddToCart
import com.jmzd.ghazal.storeappmvvm.data.models.cart.ResponseCartList
import com.jmzd.ghazal.storeappmvvm.data.models.cart.ResponseUpdateCart
import com.jmzd.ghazal.storeappmvvm.data.models.categories.ResponseCategories
import com.jmzd.ghazal.storeappmvvm.data.models.comments.BodySendComment
import com.jmzd.ghazal.storeappmvvm.data.models.comments.ResponseCommentsList
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseDetail
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductFeatures
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductPriceChart
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
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProductLike
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProfileFavorites
import com.jmzd.ghazal.storeappmvvm.data.models.profile_order.ResponseProfileOrdersList
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.BodyCoupon
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.ResponseCoupon
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.ResponseShipping
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

    @GET("auth/addresses")
    suspend fun getProfileAddresses() : Response<ResponseProfileAddresses>

    @GET("auth/address/provinces")
    suspend fun getProvinceList() : Response<ResponseProvinceList>

    @GET("auth/address/provinces")
    suspend fun getCityList(@Query("provinceId") provinceId : Int) : Response<ResponseProvinceList>

    @POST("auth/address")
    suspend fun submitAddress(@Body body: BodySubmitAddress) : Response<ResponseSubmitAddress>

    @DELETE("auth/address/remove/{address_id}")
    suspend fun removeAddress(@Path("address_id") addressId : Int) : Response<ResponseDeleteComment>

    @GET("auth/orders")
    suspend fun getOrdersList(@Query("status") status : String) : Response<ResponseProfileOrdersList>

    @GET("product/{product_id}")
    suspend fun getDetail(@Path("product_id") productId : Int) : Response<ResponseDetail>

    @POST("product/{post_id}/like")
    suspend fun postLikeProduct(@Path("post_id") postId : Int) : Response<ResponseProductLike>

    @POST("product/{productId}/add_to_cart")
    suspend fun addToCart(@Path("productId") provinceId: Int , @Body body : BodyAddToCart) : Response<SimpleResponse>

    @GET("product/{product_id}/features")
    suspend fun getDetailFeatures(@Path("product_id") provinceId: Int) : Response<ResponseProductFeatures>

    @GET("product/{product_id}/comments")
    suspend fun getDetailComments(@Path("product_id") productId: Int) : Response<ResponseCommentsList>

    @POST("product/{product_id}/comments")
    suspend fun sendComment(@Path("product_id") productId: Int , @Body body : BodySendComment) : Response<SimpleResponse>

    @GET("product/{product_id}/price-chart")
    suspend fun getPriceChart(@Path("product_id") productId: Int) : Response<ResponseProductPriceChart>

    @GET("cart")
    suspend fun getCartList() : Response<ResponseCartList>

    @PUT("cart/{id}/increment")
    suspend fun cartIncrement(@Path("id") id : Int ) : Response<ResponseUpdateCart>

    @PUT("cart/{id}/decrement")
    suspend fun cartDecrement(@Path("id") id : Int ) : Response<ResponseUpdateCart>

    @DELETE("cart/{id}")
    suspend fun cartDeleteProduct(@Path("id") id : Int ) : Response<ResponseUpdateCart>

    @GET("cart/continue")
    suspend fun cartContinue() : Response<ResponseCartList>

    @GET("shipping")
    suspend fun getShipping() : Response<ResponseShipping>

    @PUT("shipping/set/address")
    suspend fun shippingSetAddress(@Body body: BodySetAddressForShipping) : Response<ResponseSetAddressForShipping>

    @GET("check/coupon")
    suspend fun checkCoupon(@Body body : BodyCoupon) : Response<ResponseCoupon>

    @POST("payment")
    suspend fun payment(@Body body : BodyCoupon) : Response<ResponseIncreaseWallet>

}