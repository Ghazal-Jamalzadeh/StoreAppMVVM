package com.jmzd.ghazal.storeappmvvm.data.repository

import android.content.Context
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.search_filter.FilterModel
import com.jmzd.ghazal.storeappmvvm.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchFilterRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getFilterData(): MutableList<FilterModel> {

        val list = mutableListOf<FilterModel>()
        list.add(FilterModel(1, context.getString(R.string.filterNew), NEW))
        list.add(FilterModel(2, context.getString(R.string.filterExpensive), EXPENSIVE))
        list.add(FilterModel(3, context.getString(R.string.filterCheep), CHEEP))
        list.add(FilterModel(4, context.getString(R.string.filterRate), RATE))
        list.add(FilterModel(5, context.getString(R.string.filterSell), SELL))
        list.add(FilterModel(6, context.getString(R.string.filterHits), HITS))
        return list
    }
}