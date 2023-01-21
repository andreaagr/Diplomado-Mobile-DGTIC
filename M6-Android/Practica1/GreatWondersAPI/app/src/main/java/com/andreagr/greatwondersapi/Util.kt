package com.andreagr.greatwondersapi

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun showImage(
    image: String,
    imageView: ImageView,
    context: Context
) {
    Glide.with(context)
        .load(image)
        .centerCrop()
        .placeholder(R.drawable.imagen)
        .into(imageView)
}