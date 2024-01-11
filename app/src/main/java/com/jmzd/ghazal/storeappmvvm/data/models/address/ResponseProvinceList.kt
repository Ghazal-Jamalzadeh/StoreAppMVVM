package com.jmzd.ghazal.storeappmvvm.data.models.address


import com.google.gson.annotations.SerializedName
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProvinceList.ResponseProvinceListItem

class ResponseProvinceList : ArrayList<ResponseProvinceListItem>(){
    data class ResponseProvinceListItem(
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("title")
        val title: String? // آذرشهر
    )
}