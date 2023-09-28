package com.jmzd.ghazal.storeappmvvm.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.view.isVisible
import coil.load
import coil.request.CachePolicy
import com.google.android.material.snackbar.Snackbar
import com.jmzd.ghazal.storeappmvvm.R

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
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