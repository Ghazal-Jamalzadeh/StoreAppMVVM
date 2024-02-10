package com.jmzd.ghazal.storeappmvvm.data.models.cart


import com.google.gson.annotations.SerializedName

data class ResponseCartList(
    @SerializedName("delivery_price")
    val deliveryPrice: String?, // 0
    @SerializedName("final_price")
    val finalPrice: String?, // 0
    @SerializedName("id")
    val id: Int?, // 260
    @SerializedName("items_discount")
    val itemsDiscount: Int?, // 0
    @SerializedName("items_price")
    val itemsPrice: Int?, // 0
    @SerializedName("order_items")
    val orderItems: List<OrderItem>?,
    @SerializedName("quantity")
    val quantity: Int?, // 0
    @SerializedName("status")
    val status: String?, // add-to-cart
    @SerializedName("updated_at")
    val updatedAt: String?, // 2024-02-10T11:02:45.000000Z
    @SerializedName("user_id")
    val userId: String? // 262
) {
    data class OrderItem(
        @SerializedName("approved")
        val approved: String?, // 1
        @SerializedName("color_hex_code")
        val colorHexCode: String?, // #000000
        @SerializedName("color_title")
        val colorTitle: String?, // مشکی
        @SerializedName("discounted_price")
        val discountedPrice: String?, // 674900
        @SerializedName("final_price")
        val finalPrice: Int?, // 0
        @SerializedName("id")
        val id: Int?, // 571
        @SerializedName("order_id")
        val orderId: String?, // 260
        @SerializedName("price")
        val price: String?, // 6749000
        @SerializedName("product_guarantee")
        val productGuarantee: String?, // گارانتی ۱۸ ماهه هما تلکام
        @SerializedName("product_id")
        val productId: String?, // 27
        @SerializedName("product_image")
        val productImage: String?, // /storage/cache/600-0-0-width-iU4wWNM58WPlfMVXWPHDjUz5qwV0Aps6MTt3C8Kl.jpg
        @SerializedName("product_quantity")
        val productQuantity: String?, // 0
        @SerializedName("product_title")
        val productTitle: String?, // گوشی موبایل شیائومی مدل POCO X3 Pro M2102J20SG
        @SerializedName("quantity")
        val quantity: String?, // 0
        @SerializedName("updated_at")
        val updatedAt: String?, // 2024-02-10T11:02:45.000000Z
        @SerializedName("user_id")
        val userId: String? // 262
    )
}