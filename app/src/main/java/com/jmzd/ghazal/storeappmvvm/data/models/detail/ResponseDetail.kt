package com.jmzd.ghazal.storeappmvvm.data.models.detail


import com.google.gson.annotations.SerializedName

data class ResponseDetail(
    @SerializedName("brand")
    val brand: Brand?,
    @SerializedName("brand_id")
    val brandId: String?, // 13
    @SerializedName("category")
    val category: Category?,
    @SerializedName("category_id")
    val categoryId: String?, // 26
    @SerializedName("color_id")
    val colorId: List<String?>?,
    @SerializedName("colors")
    val colors: List<Color>?,
    @SerializedName("comments_avg_rate")
    val commentsAvgRate: String?, // 3.3333
    @SerializedName("comments_count")
    val commentsCount: String?, // 6
    @SerializedName("description")
    val description: String?, // <p>گوشی موبایل شیائومی مدل Poco X3 Pro دو سیم&zwnj; کارت ظرفیت 256 گیگابایت از جمله محصولات برند شیائومی که در سال 2021 روانه بازار شده است. این محصول دارای ساختاری متوازن و خوش&zwnj;ساخت بدون پشتیبانی از تکنولوژی 5G روانه بازار شده است. این محصول از بدنه پلاستیکی ساخته شده است که قاب جلو شیشه&zwnj;ای جلوه ویژه&zwnj;ای به این مدل بخشیده است. صفحه&zwnj;نمایش گوشی موبایل شیائومی مدل POCO X3 Pro دو سیم&zwnj; کارت ظرفیت 256گیگابایت در اندازه 6.67 منتشر شده است. این صفحه&zwnj;نمایش کاملاً تمام&zwnj;صفحه است و در بالا وسط اثری از بریدگی یا حفره دوربین سلفی وجود دیده می&zwnj;شود. دوربین سلفی این محصول دارای حسگر 20 مگاپیکسلی است .صحفه&zwnj;نمایش گوشی موبایل شیائومی مدل POCO X3 با استفاده از فناوری Corning Gorilla Glass 6 در برابر خط&zwnj;وخش و صدمات احتمالی محافظت می&zwnj;شود. گفتنی است چهار دوربین که سنسور اصلی آن 48 مگاپیکسلی است در قسمت پشتی این گوشی جا خوش کرده&zwnj;اند. این دوربین&zwnj;ها قادر هستند ویدئوی 4K را ثبت و ضبط کنند. دوربین&zwnj; سلفی این محصول هم به سنسوری 20 مگاپیکسلی مجهز شده است. بلوتوث نسخه 5.0، نسخه 11 سیستم عامل اندروید و باتری 5160 میلی&zwnj;آمپرساعتی از دیگر ویژگی&zwnj;&zwnj;های این گوشی جدید هستند.</p>
    @SerializedName("discount_rate")
    val discountRate: String?, // 10
    @SerializedName("discounted_price")
    val discountedPrice: String?, // 674900
    @SerializedName("end_time")
    val endTime: String?, // 2024-03-19T09:37:00.000000Z
    @SerializedName("features")
    val features: List<Feature?>?,
    @SerializedName("final_price")
    val finalPrice: Int?, // 6074100
    @SerializedName("guarantee")
    val guarantee: String?, // گارانتی ۱۸ ماهه هما تلکام
    @SerializedName("id")
    val id: Int?, // 27
    @SerializedName("image")
    val image: String?, // /storage/cache/400-0-0-width-iU4wWNM58WPlfMVXWPHDjUz5qwV0Aps6MTt3C8Kl.jpg
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("is_add_to_cart")
    val isAddToCart: Int?, // 0
    @SerializedName("is_add_to_favorite")
    val isAddToFavorite: String?, // 0
    @SerializedName("likes_count")
    val likesCount: String?, // 6
    @SerializedName("product_price")
    val productPrice: String?, // 6749000
    @SerializedName("quantity")
    val quantity: String?, // 0
    @SerializedName("slug")
    val slug: Any?, // null
    @SerializedName("status")
    val status: String?, // special
    @SerializedName("title")
    val title: String?, // گوشی موبایل شیائومی مدل POCO X3 Pro M2102J20SG
    @SerializedName("title_en")
    val titleEn: Any? // null
) {
    data class Brand(
        @SerializedName("id")
        val id: Int?, // 13
        @SerializedName("title")
        val title: Title?
    ) {
        data class Title(
            @SerializedName("en")
            val en: String?, // Xiaomi
            @SerializedName("fa")
            val fa: String? // شیائومی
        )
    }

    data class Category(
        @SerializedName("id")
        val id: Int?, // 26
        @SerializedName("parent")
        val parent: Parent?,
        @SerializedName("parent_id")
        val parentId: String?, // 11
        @SerializedName("slug")
        val slug: String?, // category-mobile-phone
        @SerializedName("title")
        val title: String? // گوشی موبایل
    ) {
        data class Parent(
            @SerializedName("id")
            val id: Int?, // 11
            @SerializedName("parent")
            val parent: Parent?,
            @SerializedName("parent_id")
            val parentId: String?, // 1
            @SerializedName("slug")
            val slug: String?, // category-mobile
            @SerializedName("title")
            val title: String? // موبایل
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
    }

    data class Color(
        @SerializedName("hex_code")
        val hexCode: String?, // #000000
        @SerializedName("id")
        val id: Int?, // 2
        @SerializedName("title")
        val title: String? // مشکی
    )

    data class Feature(
        @SerializedName("featureItem_title")
        val featureItemTitle: String?, // 6.0 اینچ و بزرگتر
        @SerializedName("feature_title")
        val featureTitle: String? // اندازه صفحه نمایش
    )
}