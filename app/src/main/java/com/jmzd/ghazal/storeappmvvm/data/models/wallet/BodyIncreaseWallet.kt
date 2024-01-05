package com.jmzd.ghazal.storeappmvvm.data.models.wallet


import com.google.gson.annotations.SerializedName

data class BodyIncreaseWallet(
    @SerializedName("amount")
    val amount: String? // 1200000
)