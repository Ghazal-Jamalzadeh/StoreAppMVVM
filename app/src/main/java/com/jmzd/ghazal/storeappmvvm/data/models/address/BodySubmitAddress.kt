package com.jmzd.ghazal.storeappmvvm.data.models.address


import com.google.gson.annotations.SerializedName

data class BodySubmitAddress(
    @SerializedName("addressId")
    val addressId: String?, // برای ویرایش
    @SerializedName("cityId")
    val cityId: String?, // شناسه شهر
    @SerializedName("floor")
    val floor: String?, // طبقه
    @SerializedName("latitude")
    val latitude: String?, // عرض جغرافیایی
    @SerializedName("longitude")
    val longitude: String?, // طول جغرافیایی
    @SerializedName("plateNumber")
    val plateNumber: String?, // شماره پلاک
    @SerializedName("postalAddress")
    val postalAddress: String?, // آدرس پستی
    @SerializedName("postalCode")
    val postalCode: String?, // کد پستی
    @SerializedName("provinceId")
    val provinceId: String?, // شناسه استان
    @SerializedName("receiverCellphone")
    val receiverCellphone: String?, // تلفن همراه گیرنده
    @SerializedName("receiverFirstname")
    val receiverFirstname: String?, // نام گیرنده
    @SerializedName("receiverLastname")
    val receiverLastname: String? // نام خانوادگی گیرنده
)