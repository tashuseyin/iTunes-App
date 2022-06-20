package com.tashuseyin.itunesapp.common.extension

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageView(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}