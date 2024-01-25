package com.jmzd.ghazal.storeappmvvm.data.models.home


import com.google.gson.annotations.SerializedName
import com.jmzd.ghazal.storeappmvvm.data.models.home.ResponseBanners.ResponseBannersItem

class ResponseBanners : ArrayList<ResponseBannersItem>(){
    data class ResponseBannersItem(
        @SerializedName("image")
        val image: String?, // media/images/banners/tLuGegnINYbXLOoEmcFO5OG8Xej52W8beg1cUUbC.jpg
        @SerializedName("link")
        val link: String?, // category
        @SerializedName("link_id") // اگر پروداکت باشد آیدی را برمیگرداند.
        val linkId: String?, // 11 //
        @SerializedName("title")
        val title: String?, // کالاهای دیجیتالی
        @SerializedName("url_link")
        val urlLink: UrlLink? // اگر کتگوری باشد این آبجکت را برمیگرداند
    ) {
        data class UrlLink(
            @SerializedName("id")
            val id: Int?, // 56
            @SerializedName("parent_id")
            val parentId: String?, // 1
            @SerializedName("slug")
            val slug: String?, // category-mobile
            @SerializedName("title")
            val title: String? // دفتر برنامه ریزی مستر راد طرح جدید کاکتوس مدل پالت کد 1587
        )
    }
}