package com.jmzd.ghazal.storeappmvvm.data.models.shipping


import com.google.gson.annotations.SerializedName

data class ResponseCoupon(
    @SerializedName("code")
    val code: String?, // poff-20
    @SerializedName("description")
    val description: String?, // تخفیف 20 درصدی
    @SerializedName("expired_at")
    val expiredAt: Any?, // null
    @SerializedName("percent")
    val percent: String?, // 20
    @SerializedName("status")
    val status: String?, // enable
    @SerializedName("title")
    val title: String?, // تخفیف 20 درصدی
    @SerializedName("type")
    val type: String? // percent
)