package com.jmzd.ghazal.storeappmvvm.data.models.comments


import com.google.gson.annotations.SerializedName

data class BodySendComment(
    @SerializedName("comment")
    val comment: String?, // توصیه میکنم
    @SerializedName("rate")
    val rate: String? // 5
)