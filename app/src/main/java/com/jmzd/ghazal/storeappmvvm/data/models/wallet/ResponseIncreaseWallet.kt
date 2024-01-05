package com.jmzd.ghazal.storeappmvvm.data.models.wallet


import com.google.gson.annotations.SerializedName

data class ResponseIncreaseWallet(
    @SerializedName("Authority")
    val authority: String?, // 000000000000000000000000000001307613
    @SerializedName("StartPay")
    val startPay: String? // https://sandbox.zarinpal.com/pg/StartPay/000000000000000000000000000001307613
)