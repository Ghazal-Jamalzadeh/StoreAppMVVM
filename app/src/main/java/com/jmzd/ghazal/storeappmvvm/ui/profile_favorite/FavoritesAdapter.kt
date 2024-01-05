package com.jmzd.ghazal.storeappmvvm.ui.profile_favorite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.data.models.profile_favorite.ResponseProfileFavorites.*
import com.jmzd.ghazal.storeappmvvm.databinding.ItemMyFavoritesBinding
import com.jmzd.ghazal.storeappmvvm.utils.BASE_URL_IMAGE
import com.jmzd.ghazal.storeappmvvm.utils.base.BaseDiffUtils
import com.jmzd.ghazal.storeappmvvm.utils.extensions.loadImage
import com.jmzd.ghazal.storeappmvvm.utils.extensions.moneySeparating
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FavoritesAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private var items = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.ViewHolder {
        val binding = ItemMyFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    inner class ViewHolder(private val binding: ItemMyFavoritesBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Data) {
            binding.apply {
                item.likeable?.let {
                    //Text
                    itemTitle.text = it.title
                    quantityTxt.text = "${it.quantity} ${context.getString(R.string.item)}"
                    priceTxt.text = it.finalPrice!!.toInt().moneySeparating()
                    //Image
                    val imageUrl = "$BASE_URL_IMAGE${it.image}"
                    itemImg.loadImage(imageUrl)
                }
                //Click
                itemTrashImg.setOnClickListener {
                    onItemClickListener?.let { it(item.id!!) }
                }
            }
        }
    }

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<Data>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}