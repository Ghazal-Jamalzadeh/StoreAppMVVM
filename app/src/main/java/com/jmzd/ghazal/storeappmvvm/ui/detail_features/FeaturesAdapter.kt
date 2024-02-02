package com.jmzd.ghazal.storeappmvvm.ui.detail_features

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.detail.ResponseProductFeatures.ResponseProductFeaturesItem
import com.jmzd.ghazal.storeappmvvm.databinding.ItemFeaturesBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FeaturesAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {

    private var items = emptyList<ResponseProductFeaturesItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeaturesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemFeaturesBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseProductFeaturesItem) {
            binding.apply {
                titleTxt.text = item.featureTitle
                infoTxt.text = item.featureItemTitle
                //Background
                if (bindingAdapterPosition % 2 == 0)
                    root.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                else
                    root.setBackgroundColor(ContextCompat.getColor(context, R.color.snow))
            }
        }
    }

    fun setData(data: List<ResponseProductFeaturesItem>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}