package com.jmzd.ghazal.storeappmvvm.data.models.profile


import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("avatar")
    val avatar: Any?, // null
    @SerializedName("birth_date")
    val birthDate: String?, // 1402-06-30T20:34:16.000000Z
    @SerializedName("cellphone")
    val cellphone: String?, // 09338975553
    @SerializedName("email")
    val email: Any?, // null
    @SerializedName("firstname")
    val firstname: Any?, // null
    @SerializedName("id")
    val id: Int?, // 174
    @SerializedName("idNumber")
    val idNumber: Any?, // null
    @SerializedName("job")
    val job: Any?, // null
    @SerializedName("lastname")
    val lastname: Any?, // null
    @SerializedName("register_date")
    val registerDate: String?, // 25 ، شهر ، 02
    @SerializedName("wallet")
    val wallet: String? // 0
)