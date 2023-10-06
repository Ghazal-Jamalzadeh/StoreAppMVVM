package com.jmzd.ghazal.storeappmvvm.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.data.models.search_filter.FilterModel
import com.jmzd.ghazal.storeappmvvm.databinding.ItemFilterBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import javax.inject.Inject

class FilterAdapter @Inject constructor() : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var items = emptyList<FilterModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterModel) {
            binding.apply {
                itemTitle.text = item.faName
                //Click
                root.setOnClickListener { onItemClickListener?.let { it(item.enName) } }
            }
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<FilterModel>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}