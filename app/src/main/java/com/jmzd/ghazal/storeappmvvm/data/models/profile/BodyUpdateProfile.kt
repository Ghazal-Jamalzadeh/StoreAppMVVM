package com.jmzd.ghazal.storeappmvvm.data.models.profile


import com.google.gson.annotations.SerializedName

data class BodyUpdateProfile(
    @SerializedName("email")
    val email: String?, // test@nouri-api.ir
    @SerializedName("firstName")
    val firstName: String?, // api
    @SerializedName("gregorianDate")
    val gregorianDate: String?, // 1993-10-25
    @SerializedName("idNumber")
    val idNumber: String?, // 0000000000
    @SerializedName("job")
    val job: String?, // Web Developer
    @SerializedName("lastName")
    val lastName: String? // nouri
)