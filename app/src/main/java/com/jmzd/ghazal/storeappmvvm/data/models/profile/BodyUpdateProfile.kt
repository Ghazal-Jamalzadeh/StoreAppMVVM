package com.jmzd.ghazal.storeappmvvm.data.models.profile


import com.google.gson.annotations.SerializedName

data class BodyUpdateProfile(
//    @SerializedName("email")
//    var email: String? = null, // test@nouri-api.ir
    @SerializedName("firstName")
    var firstName: String? = null, // api
    @SerializedName("gregorianDate")
    var gregorianDate: String? = null, // 1993-10-25
    @SerializedName("idNumber")
    var idNumber: String? = null, // 0000000000
//    @SerializedName("job")
//    var job: String? = null, // Web Developer
    @SerializedName("lastName")
    var lastName: String? = null // nouri
)