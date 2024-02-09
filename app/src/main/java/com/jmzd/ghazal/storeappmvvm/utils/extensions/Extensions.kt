package com.jmzd.ghazal.storeappmvvm.utils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.jmzd.ghazal.storeappmvvm.R
import java.text.DecimalFormat

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.showKeyboard(activity: Activity) {
    requestFocus()
    post {
        WindowCompat.getInsetsController(activity.window, this).show(WindowInsetsCompat.Type.ime())
    }
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun ImageView.loadImage(url: String) {
    this.load(url) {
        crossfade(true)
        crossfade(500)
        diskCachePolicy(CachePolicy.ENABLED)
        error(R.drawable.placeholder)
    }
}

fun ImageView.loadImageWithGlide(url: String) {
    Glide.with(context) // از خود ایمیج ویو کانتکس رو میگیریم
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}

fun View.changeVisibility(isShownLoading: Boolean, container: View) {
    //view -> progress bar
    if (isShownLoading) {
        this.isVisible = true
        container.isVisible = false
    } else {
        this.isVisible = false
        container.isVisible = true
    }
}

fun Int.moneySeparating(): String {
    return "${DecimalFormat("#,###.##").format(this)} تومان"
}

fun Int.moneySeparatingWithoutToman(): String {
    return DecimalFormat("#,###.##").format(this)
}

fun RecyclerView.setupRecyclerview(myLayoutManager: RecyclerView.LayoutManager, myAdapter: RecyclerView.Adapter<*>) {
    this.apply {
        layoutManager = myLayoutManager
        setHasFixedSize(true)
        adapter = myAdapter
    }
}

fun Dialog.transparentCorners() {
    //this -> Dialog
    this.window!!.setBackgroundDrawableResource(android.R.color.transparent)
}

@SuppressLint("Range")
fun getRealFileFromUri(context: Context, uri: Uri): String? {
    var result: String? = null
    val resolver = context.contentResolver.query(uri, null, null, null, null)
    if (resolver == null) {
        result = uri.path
    } else {
        if (resolver.moveToFirst()) {
            result = resolver.getString(resolver.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
        }
        resolver.close()
    }
    return result
}

fun Uri.openBrowser(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, this)
    ContextCompat.startActivity(context, intent, null)
}

fun ImageView.setTint(@ColorRes color: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, color)))
}

fun setTypefaceNormal(context: Context): Typeface {
    return Typeface.createFromAsset(context.assets,"fonts/iransans.ttf")
}