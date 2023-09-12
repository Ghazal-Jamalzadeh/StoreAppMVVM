package com.jmzd.ghazal.storeappmvvm.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}