package com.jmzd.ghazal.storeappmvvm.data.models.wallet


import com.google.gson.annotations.SerializedName

data class BodyIncreaseWallet(
    @SerializedName("amount")
    var amount: String? = null// 1200000
)