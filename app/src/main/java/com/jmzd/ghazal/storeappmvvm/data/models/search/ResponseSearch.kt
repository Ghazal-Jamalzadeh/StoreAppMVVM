package com.jmzd.ghazal.storeappmvvm.data.models.search


import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("products")
    val products: Products?
) {
    data class Products(
        @SerializedName("current_page")
        val currentPage: Int?, // 1
        @SerializedName("data")
        val `data`: List<Data?>?,
        @SerializedName("first_page_url")
        val firstPageUrl: String?, // https://shop.nouri-api.ir/api/v1/search?page=1
        @SerializedName("from")
        val from: Int?, // 1
        @SerializedName("last_page")
        val lastPage: Int?, // 1
        @SerializedName("last_page_url")
        val lastPageUrl: String?, // https://shop.nouri-api.ir/api/v1/search?page=1
        @SerializedName("links")
        val links: List<Link?>?,
        @SerializedName("next_page_url")
        val nextPageUrl: Any?, // null
        @SerializedName("path")
        val path: String?, // https://shop.nouri-api.ir/api/v1/search
        @SerializedName("per_page")
        val perPage: Int?, // 16
        @SerializedName("prev_page_url")
        val prevPageUrl: Any?, // null
        @SerializedName("to")
        val to: Int?, // 13
        @SerializedName("total")
        val total: Int? // 13
    ) {
        data class Data(
            @SerializedName("color_id")
            val colorId: List<String?>?,
            @SerializedName("colors")
            val colors: List<Color?>?,
            @SerializedName("comments_avg_rate")
            val commentsAvgRate: String?, // 5.0000
            @SerializedName("comments_count")
            val commentsCount: String?, // 1
            @SerializedName("created_at")
            val createdAt: String?, // 2022-01-01T13:46:55.000000Z
            @SerializedName("discount_rate")
            val discountRate: String?, // 0
            @SerializedName("discounted_price")
            val discountedPrice: Int?, // 0
            @SerializedName("end_time")
            val endTime: String?, // 2022-01-20T05:33:00.000000Z
            @SerializedName("final_price")
            val finalPrice: Int?, // 45600
            @SerializedName("id")
            val id: Int?, // 54
            @SerializedName("image")
            val image: String?, // /storage/cache/400-0-0-width-ZQuPFtmJrpBtD15Nt16EaJN2vHJ5UFC3hfEVwN8W.jpg
            @SerializedName("product_price")
            val productPrice: String?, // 1050000
            @SerializedName("quantity")
            val quantity: String?, // 24
            @SerializedName("status")
            val status: String?, // normal
            @SerializedName("title")
            val title: String? // پایه نگهدارنده گوشی موبایل شیائومی مدل WCJ02ZM
        ) {
            data class Color(
                @SerializedName("hex_code")
                val hexCode: String? // #000000
            )
        }

        data class Link(
            @SerializedName("active")
            val active: Boolean?, // false
            @SerializedName("label")
            val label: String?, // &laquo; قبلی
            @SerializedName("url")
            val url: String? // https://shop.nouri-api.ir/api/v1/search?page=1
        )
    }
}