package com.jmzd.ghazal.storeappmvvm.data.models.wallet


import com.google.gson.annotations.SerializedName

data class ResponseIncreaseWallet(
    @SerializedName("Authority")
    val authority: String?, // 000000000000000000000000000001307613
    @SerializedName("StartPay")
    val startPay: String? ,  // https://sandbox.zarinpal.com/pg/StartPay/000000000000000000000000000001307613
    @SerializedName("message")
    val message: String? // in api age ba error roo be roo she be jaye do ta fielde bala message ra barmigardoone
)