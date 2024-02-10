package com.jmzd.ghazal.storeappmvvm.ui.cart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.cart.ResponseCartList.*
import com.jmzd.ghazal.storeappmvvm.databinding.ItemCartBinding
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import com.jmzd.ghazal.storeappmvvm.utils.constants.BASE_URL_IMAGE
import com.jmzd.ghazal.storeappmvvm.utils.constants.DECREMENT
import com.jmzd.ghazal.storeappmvvm.utils.constants.DELETE
import com.jmzd.ghazal.storeappmvvm.utils.constants.INCREMENT
import com.jmzd.ghazal.storeappmvvm.utils.extensions.loadImage
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CartAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var items = emptyList<OrderItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: OrderItem) {
            binding.apply {
                itemTitle.text = item.productTitle
                itemCountTxt.text = item.quantity
                colorTxt.text = item.colorTitle
                //Image
                val imageUrl = "$BASE_URL_IMAGE${item.productImage}"
                itemImg.loadImage(imageUrl)
                //Guarantee
                if (item.productGuarantee.isNullOrEmpty().not()) {
                    guaranteeLay.isVisible = true
                    guaranteeTxt.text = item.productGuarantee
                } else {
                    guaranteeLay.isVisible = false
                }
                //Discount
                if (item.discountedPrice!!.toInt() > 0) {
                    itemPriceDiscount.isVisible = true
                    itemPrice.apply {
                        isVisible = true
                        text = item.finalPrice.toString().toInt().moneySeparating()
                    }
                    itemPriceDiscount.apply {
                        text = (item.quantity.toString().toInt() * item.price.toString().toInt()).moneySeparating()
                        paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        setTextColor(ContextCompat.getColor(context, R.color.salmon))
                    }
                } else {
                    itemPriceDiscount.isVisible = false
                    itemPrice.apply {
                        text = item.price.toString().toInt().moneySeparating()
                        setTextColor(ContextCompat.getColor(context, R.color.darkTurquoise))
                    }
                }
                //Trash / Minus
                if (item.quantity.toString().toInt() == 1) {
                    itemMinusImg.visibility = View.INVISIBLE
                    itemTrashImg.isVisible = true
                } else {
                    itemMinusImg.isVisible = true
                    itemTrashImg.isVisible = false
                }
                //Plus
                if (item.quantity.toString().toInt() == item.productQuantity.toString().toInt()) {
                    itemPlusImg.apply {
                        alpha = 0.2f
                        isEnabled = false
                    }
                } else {
                    itemPlusImg.apply {
                        alpha = 1.0f
                        isEnabled = true
                    }
                }
                //Click
                itemPlusImg.setOnClickListener { onItemClickListener?.let { it(item.id!!, INCREMENT) } }
                itemMinusImg.setOnClickListener { onItemClickListener?.let { it(item.id!!, DECREMENT) } }
                itemTrashImg.setOnClickListener { onItemClickListener?.let { it(item.id!!, DELETE) } }
            }
        }
    }

    private var onItemClickListener: ((Int, String) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int, String) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<OrderItem>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}