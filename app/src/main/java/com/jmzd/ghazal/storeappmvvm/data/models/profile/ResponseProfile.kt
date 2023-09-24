package com.jmzd.ghazal.storeappmvvm.data.models.profile


import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @SerializedName("avatar")
    val avatar: String?, // null
    @SerializedName("birth_date")
    val birthDate: String?, // 1402-06-30T20:34:16.000000Z
    @SerializedName("cellphone")
    val cellphone: String?, // 09338975553
    @SerializedName("email")
    val email: String?, // null
    @SerializedName("firstname")
    val firstname: String?, // null
    @SerializedName("id")
    val id: Int?, // 174
    @SerializedName("idNumber")
    val idNumber: String?, // null
    @SerializedName("job")
    val job: String?, // null
    @SerializedName("lastname")
    val lastname: String?, // null
    @SerializedName("register_date")
    val registerDate: String?, // 25 ، شهر ، 02
    @SerializedName("wallet")
    val wallet: String? // 0
)