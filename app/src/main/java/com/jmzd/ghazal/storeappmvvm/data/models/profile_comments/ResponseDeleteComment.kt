package com.jmzd.ghazal.storeappmvvm.data.models.profile_comments


import com.google.gson.annotations.SerializedName

data class ResponseDeleteComment(
    @SerializedName("message")
    val message: String? // success
)