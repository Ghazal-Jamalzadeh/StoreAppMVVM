package com.jmzd.ghazal.storeappmvvm.ui.shipping.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.data.models.shipping.ResponseShipping.Order.OrderItem
import com.jmzd.ghazal.storeappmvvm.databinding.ItemShippingProductBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import com.jmzd.ghazal.storeappmvvm.utils.constants.BASE_URL_IMAGE
import com.jmzd.ghazal.storeappmvvm.utils.extensions.loadImageWithGlide
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ShippingAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<ShippingAdapter.ViewHolder>() {

    private var items = emptyList<OrderItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShippingProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemShippingProductBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: OrderItem) {
            binding.apply {
                val image = "$BASE_URL_IMAGE${item.productImage}"
                itemImg.loadImageWithGlide(image)
                //Title
                itemTitle.text = item.productTitle
            }
        }
    }

    fun setData(data: List<OrderItem>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}