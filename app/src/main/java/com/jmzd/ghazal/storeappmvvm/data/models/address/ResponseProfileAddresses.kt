package com.jmzd.ghazal.storeappmvvm.data.models.address


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


class ResponseProfileAddresses : ArrayList<ResponseProfileAddresses.ResponseProfileAddressesItem>() {
    @Parcelize
    data class ResponseProfileAddressesItem(
        @SerializedName("approved")
        val approved: String?, // 1
        @SerializedName("city")
        val city: @RawValue City?,
        @SerializedName("city_id")
        val cityId: String?, // 329
        @SerializedName("created_at")
        val createdAt: String?, // 2023-05-20T08:02:52.000000Z
        @SerializedName("deleted_at")
        val deletedAt: @RawValue Any?, // null
        @SerializedName("floor")
        val floor: String?, // 5
        @SerializedName("id")
        val id: Int?, // 7
        @SerializedName("latitude")
        val latitude: String?, // 4.00000000
        @SerializedName("longitude")
        val longitude: String?, // 5.00000000
        @SerializedName("plate_number")
        val plateNumber: String?, // 845
        @SerializedName("postal_address")
        val postalAddress: String?, // تهران - خیابان اینجا
        @SerializedName("postal_code")
        val postalCode: String?, // 5784567895
        @SerializedName("province")
        val province: @RawValue Province?,
        @SerializedName("province_id")
        val provinceId: String?, // 8
        @SerializedName("receiver_cellphone")
        val receiverCellphone: String?, // 09121112233
        @SerializedName("receiver_firstname")
        val receiverFirstname: String?, // محمد
        @SerializedName("receiver_lastname")
        val receiverLastname: String?, // نوری
        @SerializedName("updated_at")
        val updatedAt: String?, // 2023-06-07T09:12:31.000000Z
        @SerializedName("user_id")
        val userId: String? // 1
    ) : Parcelable {
        data class City(
            @SerializedName("id")
            val id: Int?, // 329
            @SerializedName("title")
            val title: String? // تهران
        )

        data class Province(
            @SerializedName("id")
            val id: Int?, // 8
            @SerializedName("title")
            val title: String? // تهران
        )
    }
}