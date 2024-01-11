package com.jmzd.ghazal.storeappmvvm.ui.profile_address

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.address.ResponseProfileAddresses.ResponseProfileAddressesItem
import com.jmzd.ghazal.storeappmvvm.databinding.ItemAddressesBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AddressesAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<AddressesAdapter.ViewHolder>() {

    private var items = emptyList<ResponseProfileAddressesItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesAdapter.ViewHolder {
        val binding = ItemAddressesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressesAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemAddressesBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseProfileAddressesItem) {
            binding.apply {
                //Text
                nameTxt.text = "${item.receiverFirstname} ${item.receiverLastname}"
                provinceTxt.text = item.province?.title
                cityTxt.text = item.city?.title
                locationTxt.text = "${item.postalAddress} - ${context.getString(R.string.plateNumber)} ${item.plateNumber} - " +
                        "${context.getString(R.string.floor)} ${item.floor}"
                postalTxt.text = item.postalCode
                phoneTxt.text = item.receiverCellphone
                //Click
                root.setOnClickListener {
                    onItemClickListener?.let { it(item) }
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseProfileAddressesItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseProfileAddressesItem) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseProfileAddressesItem>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}