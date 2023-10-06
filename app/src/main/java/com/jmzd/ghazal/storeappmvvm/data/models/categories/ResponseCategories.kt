package com.jmzd.ghazal.storeappmvvm.data.models.categories


import com.google.gson.annotations.SerializedName
/* flat json */
class ResponseCategories : ArrayList<ResponseCategories.ResponseCategoriesItem>(){
    data class ResponseCategoriesItem(
        @SerializedName("icon")
        val icon: String?, // fas fa-laptop
        @SerializedName("icon_type")
        val iconType: Int?, // 0
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("slug")
        val slug: String?, // electronic-devices
        @SerializedName("sub_categories")
        val subCategories: List<SubCategory>?,
        @SerializedName("title")
        val title: String? // کالای دیجیتال
    ) {
        data class SubCategory(
            @SerializedName("id")
            val id: Int?, // 9
            @SerializedName("parent_id")
            val parentId: String?, // 1
            @SerializedName("slug")
            val slug: String?, // category-computer-parts
            @SerializedName("sub_categories")
            val subCategories: List<SubCategory?>?,
            @SerializedName("title")
            val title: String? // کامپیوتر و تجهیزات جانبی
        ) {
            data class SubCategory(
                @SerializedName("id")
                val id: Int?, // 31
                @SerializedName("parent_id")
                val parentId: String?, // 9
                @SerializedName("slug")
                val slug: String?, // category-monitor
                @SerializedName("sub_categories")
                val subCategories: List<Any?>?,
                @SerializedName("title")
                val title: String? // مانیتور
            )
        }
    }
}