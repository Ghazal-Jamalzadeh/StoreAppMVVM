package com.jmzd.ghazal.storeappmvvm.data.models.address


import com.google.gson.annotations.SerializedName

data class BodySubmitAddress(
    @SerializedName("addressId")
    var addressId: String? = null, // برای ویرایش
    @SerializedName("cityId")
    var cityId: String? = null, // شناسه شهر
    @SerializedName("floor")
    var floor: String? = null, // طبقه
    @SerializedName("latitude")
    var latitude: String? = null, // عرض جغرافیایی
    @SerializedName("longitude")
    var longitude: String? = null, // طول جغرافیایی
    @SerializedName("plateNumber")
    var plateNumber: String? = null, // شماره پلاک
    @SerializedName("postalAddress")
    var postalAddress: String? = null, // آدرس پستی
    @SerializedName("postalCode")
    var postalCode: String? = null, // کد پستی
    @SerializedName("provinceId")
    var provinceId: String? = null, // شناسه استان
    @SerializedName("receiverCellphone")
    var receiverCellphone: String? = null, // تلفن همراه گیرنده
    @SerializedName("receiverFirstname")
    var receiverFirstname: String? = null, // نام گیرنده
    @SerializedName("receiverLastname")
    var receiverLastname: String? = null // نام خانوادگی گیرنده
)