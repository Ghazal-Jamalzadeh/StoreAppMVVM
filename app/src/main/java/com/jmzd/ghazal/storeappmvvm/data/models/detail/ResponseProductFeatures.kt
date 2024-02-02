package com.jmzd.ghazal.storeappmvvm.data.models.detail


import com.google.gson.annotations.SerializedName
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductFeatures.ResponseProductFeaturesItem

class ResponseProductFeatures : ArrayList<ResponseProductFeaturesItem>(){
    data class ResponseProductFeaturesItem(
        @SerializedName("featureItem_title")
        val featureItemTitle: String?, // 6.0 اینچ و بزرگتر
        @SerializedName("feature_title")
        val featureTitle: String? // اندازه صفحه نمایش
    )
}