package com.jmzd.ghazal.storeappmvvm.ui.detail.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jmzd.ghazal.storeappmvvm.ui.detail_chart.DetailChartFragment
import com.jmzd.ghazal.storeappmvvm.ui.detail_comments.DetailCommentsFragment
import com.jmzd.ghazal.storeappmvvm.ui.detail_features.DetailFeaturesFragment

class PagerAdapter(manager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailCommentsFragment()
            1 -> DetailFeaturesFragment()
            else -> DetailChartFragment()
        }
    }
}