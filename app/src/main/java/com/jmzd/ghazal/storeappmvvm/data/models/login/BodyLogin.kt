package com.jmzd.ghazal.storeappmvvm.data.models.login


import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class BodyLogin(
    @SerializedName("login")
    var login: String? = null, //09120174757
    @SerializedName("hash_code")
    var hashCode: String? = null, //hjfvhzdbg#+
)