package com.jmzd.ghazal.storeappmvvm.data.models.shipping

import com.google.gson.annotations.SerializedName

data class ResponseShipping(
    @SerializedName("addresses")
    val addresses: List<Addresse>?,
    @SerializedName("order")
    val order: Order?,
    @SerializedName("status")
    val status: Boolean? // true
) {
    data class Addresse(
        @SerializedName("id")
        val id: Int?, // 39
        @SerializedName("postal_address")
        val postalAddress: String?, // خیابون اینجا
        @SerializedName("receiver_cellphone")
        val receiverCellphone: String?, // 09120000000
        @SerializedName("receiver_firstname")
        val receiverFirstname: String?, // گوهری
        @SerializedName("receiver_lastname")
        val receiverLastname: String? // علی
    )

    data class Order(
        @SerializedName("address")
        val address: Address?,
        @SerializedName("address_id")
        val addressId: String?, // 39
        @SerializedName("delivery_price")
        val deliveryPrice: String?, // 0
        @SerializedName("final_price")
        val finalPrice: String?, // 8417000
        @SerializedName("id")
        val id: Int?, // 153
        @SerializedName("items_discount")
        val itemsDiscount: String?, // 0
        @SerializedName("items_price")
        val itemsPrice: String?, // 8417000
        @SerializedName("order_items")
        val orderItems: List<OrderItem>,
        @SerializedName("quantity")
        val quantity: String? // 2
    ) {
        data class Address(
            @SerializedName("id")
            val id: Int?, // 39
            @SerializedName("postal_address")
            val postalAddress: String?, // خیابون اینجا
            @SerializedName("receiver_cellphone")
            val receiverCellphone: String?, // 09120000000
            @SerializedName("receiver_firstname")
            val receiverFirstname: String?, // گوهری
            @SerializedName("receiver_lastname")
            val receiverLastname: String? // علی
        )

        data class OrderItem(
            @SerializedName("color_hex_code")
            val colorHexCode: String?, // #c77b30
            @SerializedName("color_title")
            val colorTitle: String?, // برنز
            @SerializedName("discounted_price")
            val discountedPrice: String?, // 0
            @SerializedName("order_id")
            val orderId: String?, // 153
            @SerializedName("product_id")
            val productId: String?, // 26
            @SerializedName("product_image")
            val productImage: String?, // /storage/cache/300-0-0-width-bBuf5DUdzAjSYxENV6ssdGwGqckqzdZVnNVZcKhj.jpg
            @SerializedName("product_title")
            val productTitle: String?, // گوشی موبایل شیائومی مدل Redmi Note 10 pro M2101K6G
            @SerializedName("quantity")
            val quantity: String? // 1
        )
    }
}