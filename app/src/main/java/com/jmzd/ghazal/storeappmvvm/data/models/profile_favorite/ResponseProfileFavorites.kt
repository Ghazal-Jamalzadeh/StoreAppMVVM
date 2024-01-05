package com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite


import com.google.gson.annotations.SerializedName

data class ResponseProfileFavorites(
    @SerializedName("current_page")
    val currentPage: Int?, // 1
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("first_page_url")
    val firstPageUrl: String?, // http://larashop.site/api/v1/auth/favorites?page=1
    @SerializedName("from")
    val from: Int?, // 1
    @SerializedName("last_page")
    val lastPage: Int?, // 4
    @SerializedName("last_page_url")
    val lastPageUrl: String?, // http://larashop.site/api/v1/auth/favorites?page=4
    @SerializedName("links")
    val links: List<Link?>?,
    @SerializedName("next_page_url")
    val nextPageUrl: String?, // http://larashop.site/api/v1/auth/favorites?page=2
    @SerializedName("path")
    val path: String?, // http://larashop.site/api/v1/auth/favorites
    @SerializedName("per_page")
    val perPage: Int?, // 1
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?, // null
    @SerializedName("to")
    val to: Int?, // 1
    @SerializedName("total")
    val total: Int? // 4
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String?, // 2023-01-23T07:44:15.000000Z
        @SerializedName("id")
        val id: Int?, // 4
        @SerializedName("likeable")
        val likeable: Likeable?,
        @SerializedName("likeable_id")
        val likeableId: Int?, // 31
        @SerializedName("likeable_type")
        val likeableType: String?, // App\\Models\\Product
        @SerializedName("updated_at")
        val updatedAt: String?, // 2023-01-23T07:44:15.000000Z
        @SerializedName("user_id")
        val userId: Int? // 6
    ) {
        data class Likeable(
            @SerializedName("discount_rate")
            val discountRate: String?, // 4
            @SerializedName("discounted_price")
            val discountedPrice: Int?, // 0
            @SerializedName("end_time")
            val endTime: String?, // 2022-01-20T13:39:00.000000Z
            @SerializedName("final_price")
            val finalPrice: String?, // 25100000
            @SerializedName("id")
            val id: Int?, // 31
            @SerializedName("image")
            val image: String?, // /storage/cache/600-0-0-width-qZgtL2yhRZ2xtecEioIxA7DrLbHl2EoMEJMHW3ZP.jpg
            @SerializedName("product_price")
            val productPrice: String?, // 25100000
            @SerializedName("quantity")
            val quantity: Int?, // 13
            @SerializedName("status")
            val status: String?, // offer
            @SerializedName("title")
            val title: String? // گوشی موبایل اپل مدل iPhone 12 A2404 ZAA
        )
    }

    data class Link(
        @SerializedName("active")
        val active: Boolean?, // false
        @SerializedName("label")
        val label: String?, // &laquo; قبلی
        @SerializedName("url")
        val url: String? // http://larashop.site/api/v1/auth/favorites?page=1
    )
}