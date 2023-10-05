package com.jmzd.ghazal.storeappmvvm.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.data.models.search.ResponseSearch.Products.Data.Color
import com.jmzd.ghazal.storeappmvvm.databinding.ItemColorsListBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import javax.inject.Inject

class ColorsAdapter @Inject constructor() : RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    private var items = emptyList<Color>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsAdapter.ViewHolder {
        val binding = ItemColorsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorsAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemColorsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Color) {
            binding.apply {
                itemsColor.setBackgroundColor(android.graphics.Color.parseColor(item.hexCode))
            }
        }
    }

    fun setData(data: List<Color>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}