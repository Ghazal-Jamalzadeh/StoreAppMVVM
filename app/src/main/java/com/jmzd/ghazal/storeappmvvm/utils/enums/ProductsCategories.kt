package com.jmzd.ghazal.storeappmvvm.utils.enums

import com.jmzd.ghazal.storeappmvvm.utils.constants.CATEGORY_LAPTOP
import com.jmzd.ghazal.storeappmvvm.utils.constants.CATEGORY_MEN_SHOES
import com.jmzd.ghazal.storeappmvvm.utils.constants.CATEGORY_MOBILE_PHONE
import com.jmzd.ghazal.storeappmvvm.utils.constants.CATEGORY_STATIONERY

enum class ProductsCategories(val label: String) {
    MOBILE(CATEGORY_MOBILE_PHONE),
    SHOES(CATEGORY_MEN_SHOES),
    STATIONERY(CATEGORY_STATIONERY),
    LAPTOP(CATEGORY_LAPTOP)
}