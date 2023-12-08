package com.jmzd.ghazal.storeappmvvm.data.models.home


import com.google.gson.annotations.SerializedName
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch

data class ResponseProducts(
    @SerializedName("subCategory")
    val subCategory: SubCategory?
) {
    data class SubCategory(
        @SerializedName("id")
        val id: Int?, // 31
        @SerializedName("parent")
        val parent: Parent?,
        @SerializedName("parent_id")
        val parentId: String?, // 9
        @SerializedName("products")
        val products: Products?,
        @SerializedName("products_count")
        val productsCount: Int?, // 3
        @SerializedName("slug")
        val slug: String?, // category-monitor
        @SerializedName("sub_categories")
        val subCategories: List<Any?>?,
        @SerializedName("title")
        val title: String? // مانیتور
    ) {
        data class Parent(
            @SerializedName("id")
            val id: Int?, // 9
            @SerializedName("parent")
            val parent: Parent?,
            @SerializedName("parent_id")
            val parentId: String?, // 1
            @SerializedName("slug")
            val slug: String?, // category-computer-parts
            @SerializedName("title")
            val title: String? // کامپیوتر و تجهیزات جانبی
        ) {
            data class Parent(
                @SerializedName("id")
                val id: Int?, // 1
                @SerializedName("parent")
                val parent: Any?, // null
                @SerializedName("parent_id")
                val parentId: String?, // 0
                @SerializedName("slug")
                val slug: String?, // electronic-devices
                @SerializedName("title")
                val title: String? // کالای دیجیتال
            )
        }

        data class Products(
            @SerializedName("current_page")
            val currentPage: Int?, // 1
            @SerializedName("data")
            val `data`: List<Data>?,
            @SerializedName("first_page_url")
            val firstPageUrl: String?, // https://shop.nouri-api.ir/api/v1/category/pro/category-monitor?page=1
            @SerializedName("from")
            val from: Int?, // 1
            @SerializedName("last_page")
            val lastPage: Int?, // 1
            @SerializedName("last_page_url")
            val lastPageUrl: String?, // https://shop.nouri-api.ir/api/v1/category/pro/category-monitor?page=1
            @SerializedName("links")
            val links: List<Link?>?,
            @SerializedName("next_page_url")
            val nextPageUrl: Any?, // null
            @SerializedName("path")
            val path: String?, // https://shop.nouri-api.ir/api/v1/category/pro/category-monitor
            @SerializedName("per_page")
            val perPage: Int?, // 16
            @SerializedName("prev_page_url")
            val prevPageUrl: Any?, // null
            @SerializedName("to")
            val to: Int?, // 3
            @SerializedName("total")
            val total: Int? // 3
        ) {
            data class Data(
                @SerializedName("color_id")
                val colorId: List<String?>?,
                @SerializedName("colors")
                val colors: List<ResponseSearch.Products.Data.Color>?,
                @SerializedName("comments_avg_rate")
                val commentsAvgRate: String?, // 3.1250
                @SerializedName("comments_count")
                val commentsCount: String?, // 8
                @SerializedName("created_at")
                val createdAt: String?, // 2022-01-01T15:06:43.000000Z
                @SerializedName("discount_rate")
                val discountRate: String?, // 25
                @SerializedName("discounted_price")
                val discountedPrice: Int?, // 0
                @SerializedName("end_time")
                val endTime: String?, // 2024-03-19T09:37:00.000000Z
                @SerializedName("final_price")
                val finalPrice: Int?, // 11190000
                @SerializedName("id")
                val id: Int?, // 62
                @SerializedName("image")
                val image: String?, // /storage/cache/400-0-0-width-qmwHY5OV12kOBAMlr5MpWW4VOdVoF5QsAMPt72vW.jpg
                @SerializedName("product_price")
                val productPrice: String?, // 14920000
                @SerializedName("quantity")
                val quantity: String?, // 0
                @SerializedName("status")
                val status: String?, // discount
                @SerializedName("title")
                val title: String?, // مانیتور ایسوس مدل VG27WQ سایز 27 اینچ
                @SerializedName("title_en")
                val titleEn: String? // monitor asus VG27WQ 27 inch
            )

            data class Link(
                @SerializedName("active")
                val active: Boolean?, // false
                @SerializedName("label")
                val label: String?, // &laquo; قبلی
                @SerializedName("url")
                val url: String? // https://shop.nouri-api.ir/api/v1/category/pro/category-monitor?page=1
            )
        }
    }
}