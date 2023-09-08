package com.jmzd.ghazal.storeappmvvm.data.models.login


import com.google.gson.annotations.SerializedName

data class BodyLogin(
    @SerializedName("login")
    val login: String?, //09120174757
    @SerializedName("hash_code")
    val hashCode: String?, //hjfvhzdbg#+
)