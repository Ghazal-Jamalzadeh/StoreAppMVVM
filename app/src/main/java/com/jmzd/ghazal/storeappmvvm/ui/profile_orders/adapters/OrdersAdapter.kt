package com.jmzd.ghazal.storeappmvvm.ui.profile_orders.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.profile_order.ResponseProfileOrdersList.Data
import com.jmzd.ghazal.storeappmvvm.databinding.ItemOrdersBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import com.jmzd.ghazal.storeappmvvm.utils.extensions.convertDateToFarsi
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import com.jmzd.ghazal.storeappmvvm.utils.extensions.setupRecyclerview
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OrdersAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private var items = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.ViewHolder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Data) {
            binding.apply {
                itemPrice.text = item.finalPrice.toString().toInt().moneySeparating()
                //Convert date
                val dateSplit = item.updatedAt!!.split("T")
                val date = dateSplit[0].convertDateToFarsi()
                val hour = "${context.getString(R.string.hour)} ${
                    dateSplit[1].split(".")[0].dropLast(3)
                }"
                calendarTitle.text = "$date | $hour"
                //Products
                item.orderItems?.let { products ->
                    productsList(products, binding)
                }
            }
        }
    }

    private fun productsList(list: List<Data.OrderItem>, binding: ItemOrdersBinding) {
        val adapter = ProductsAdapter()
        adapter.setData(list)
        binding.productsList.setupRecyclerview(LinearLayoutManager(context), adapter)
    }

    fun setData(data: List<Data>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}