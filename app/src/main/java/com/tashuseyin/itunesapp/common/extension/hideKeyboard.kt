package com.tashuseyin.itunesapp.common.extension

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun hideKeyboard(view: View) {
    val insetsController = ViewCompat.getWindowInsetsController(view)
    insetsController?.hide(WindowInsetsCompat.Type.ime())
}