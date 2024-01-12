package com.jmzd.ghazal.storeappmvvm.data.models.address


import com.google.gson.annotations.SerializedName

data class ResponseSubmitAddress(
    @SerializedName("city_id")
    val cityId: String?, // 241
    @SerializedName("created_at")
    val createdAt: String?, // 2023-07-22T12:31:22.000000Z
    @SerializedName("floor")
    val floor: String?, // 2
    @SerializedName("id")
    val id: Int?, // 39
    @SerializedName("latitude")
    val latitude: String?, // 35.0
    @SerializedName("longitude")
    val longitude: String?, // 36.0
    @SerializedName("plate_number")
    val plateNumber: String?, // 86
    @SerializedName("postal_address")
    val postalAddress: String?, // خیابون اینجا
    @SerializedName("postal_code")
    val postalCode: String?, // 1234567895
    @SerializedName("province_id")
    val provinceId: String?, // 5
    @SerializedName("receiver_cellphone")
    val receiverCellphone: String?, // 09120000000
    @SerializedName("receiver_firstname")
    val receiverFirstname: String?, // گوهری
    @SerializedName("receiver_lastname")
    val receiverLastname: String?, // علی
    @SerializedName("updated_at")
    val updatedAt: String?, // 2023-07-22T12:31:22.000000Z
    @SerializedName("user_id")
    val userId: Int? // 1
)