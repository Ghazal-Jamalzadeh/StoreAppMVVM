package com.jmzd.ghazal.storeappmvvm.data.models.comments


import com.google.gson.annotations.SerializedName

data class BodySendComment(
    @SerializedName("comment")
    var comment: String? = null, // توصیه میکنم
    @SerializedName("rate")
    var rate: String? = null  // 5
)