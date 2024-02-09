package com.jmzd.ghazal.storeappmvvm.data.models.detail


import com.google.gson.annotations.SerializedName
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductPriceChart.*

class ResponseProductPriceChart : ArrayList<ResponseProductPriceChartItem>(){
    data class ResponseProductPriceChartItem(
        @SerializedName("day")
        val day: String?, // 1402-11-20
        @SerializedName("price")
        val price: Int? // 6074100
    )
}