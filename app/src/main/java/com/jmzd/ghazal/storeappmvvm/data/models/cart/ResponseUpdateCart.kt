package com.jmzd.ghazal.storeappmvvm.data.models.cart


import com.google.gson.annotations.SerializedName

data class ResponseUpdateCart(
    @SerializedName("discounted_price")
    val discountedPrice: String?, // 0
    @SerializedName("final_price")
    val finalPrice: Int?, // 25167000
    @SerializedName("id")
    val id: Int?, // 317
    @SerializedName("order_id")
    val orderId: String?, // 153
    @SerializedName("price")
    val price: String?, // 8389000
    @SerializedName("product_id")
    val productId: String?, // 26
    @SerializedName("quantity")
    val quantity: Int?, // 3
    @SerializedName("updated_at")
    val updatedAt: String?, // 2023-09-11T15:26:18.000000Z
    @SerializedName("user_id")
    val userId: String? // 1
)