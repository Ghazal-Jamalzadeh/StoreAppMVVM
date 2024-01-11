package com.jmzd.ghazal.storeappmvvm.data.models.address


import com.google.gson.annotations.SerializedName

data class ResponseProfileAddresses(
    @SerializedName("current_page")
    val currentPage: Int?, // 1
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("first_page_url")
    val firstPageUrl: String?, // http://larashop.site/api/v1/auth/comments?page=1
    @SerializedName("from")
    val from: Int?, // 1
    @SerializedName("last_page")
    val lastPage: Int?, // 3
    @SerializedName("last_page_url")
    val lastPageUrl: String?, // http://larashop.site/api/v1/auth/comments?page=3
    @SerializedName("links")
    val links: List<Link?>?,
    @SerializedName("next_page_url")
    val nextPageUrl: String?, // http://larashop.site/api/v1/auth/comments?page=2
    @SerializedName("path")
    val path: String?, // http://larashop.site/api/v1/auth/comments
    @SerializedName("per_page")
    val perPage: Int?, // 1
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?, // null
    @SerializedName("to")
    val to: Int?, // 1
    @SerializedName("total")
    val total: Int? // 3
) {
    data class Data(
        @SerializedName("approved")
        val approved: Int?, // 0
        @SerializedName("comment")
        val comment: String?, // عالیه
        @SerializedName("created_at")
        val createdAt: String?, // 2023-01-23T08:29:37.000000Z
        @SerializedName("id")
        val id: Int?, // 8
        @SerializedName("product")
        val product: Product?,
        @SerializedName("product_id")
        val productId: Int?, // 27
        @SerializedName("rate")
        val rate: Int?, // 4
        @SerializedName("updated_at")
        val updatedAt: String?, // 2023-01-23T08:29:37.000000Z
        @SerializedName("user_id")
        val userId: Int? // 6
    ) {
        data class Product(
            @SerializedName("id")
            val id: Int?, // 27
            @SerializedName("image")
            val image: String?, // /storage/cache/600-0-0-width-iU4wWNM58WPlfMVXWPHDjUz5qwV0Aps6MTt3C8Kl.jpg
            @SerializedName("title")
            val title: String? // گوشی موبایل شیائومی مدل POCO X3 Pro M2102J20SG
        )
    }

    data class Link(
        @SerializedName("active")
        val active: Boolean?, // false
        @SerializedName("label")
        val label: String?, // &laquo; قبلی
        @SerializedName("url")
        val url: String? // http://larashop.site/api/v1/auth/comments?page=1
    )
}