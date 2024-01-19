package com.jmzd.ghazal.storeappmvvm.data.models.profile_order


import com.google.gson.annotations.SerializedName

data class ResponseProfileOrdersList(
    @SerializedName("current_page")
    val currentPage: Int?, // 1
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("first_page_url")
    val firstPageUrl: String?, // https://shop.nouri-api.ir/api/v1/auth/orders?page=1
    @SerializedName("from")
    val from: Int?, // 1
    @SerializedName("last_page")
    val lastPage: Int?, // 2
    @SerializedName("last_page_url")
    val lastPageUrl: String?, // https://shop.nouri-api.ir/api/v1/auth/orders?page=2
    @SerializedName("links")
    val links: List<Link?>?,
    @SerializedName("next_page_url")
    val nextPageUrl: String?, // https://shop.nouri-api.ir/api/v1/auth/orders?page=2
    @SerializedName("path")
    val path: String?, // https://shop.nouri-api.ir/api/v1/auth/orders
    @SerializedName("per_page")
    val perPage: Int?, // 10
    @SerializedName("prev_page_url")
    val prevPageUrl: Any?, // null
    @SerializedName("to")
    val to: Int?, // 10
    @SerializedName("total")
    val total: Int? // 16
) {
    data class Data(
        @SerializedName("final_price")
        val finalPrice: String?, // 19579000
        @SerializedName("id")
        val id: Int?, // 110
        @SerializedName("order_items")
        val orderItems: List<OrderItem>?,
        @SerializedName("status")
        val status: String?, // payment-cancel
        @SerializedName("updated_at")
        val updatedAt: String? // 2023-06-08T08:25:24.000000Z
    ) {
        data class OrderItem(
            @SerializedName("extras")
            val extras: Extras?,
            @SerializedName("order_id")
            val orderId: String?, // 110
            @SerializedName("product_id")
            val productId: String?, // 26
            @SerializedName("product_title")
            val productTitle: String? // گوشی موبایل شیائومی مدل Redmi Note 10 pro M2101K6G
        ) {
            data class Extras(
                @SerializedName("color")
                val color: String?, // 6
                @SerializedName("discounted_price")
                val discountedPrice: String?, // 0
                @SerializedName("guarantee")
                val guarantee: String?, // گارانتی ۱۸ ماهه سی تلکام
                @SerializedName("id")
                val id: Int?, // 26
                @SerializedName("image")
                val image: String?, // /storage/cache/150-0-0-width-bBuf5DUdzAjSYxENV6ssdGwGqckqzdZVnNVZcKhj.jpg
                @SerializedName("price")
                val price: String?, // 8389000
                @SerializedName("quantity")
                val quantity: String?, // 1
                @SerializedName("title")
                val title: String? // گوشی موبایل شیائومی مدل Redmi Note 10 pro M2101K6G
            )
        }
    }

    data class Link(
        @SerializedName("active")
        val active: Boolean?, // false
        @SerializedName("label")
        val label: String?, // &laquo; قبلی
        @SerializedName("url")
        val url: String? // https://shop.nouri-api.ir/api/v1/auth/orders?page=1
    )
}