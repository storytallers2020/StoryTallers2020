package ru.storytellers.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide


fun loadImage(url: String, container: ImageView) {
    Glide.with(container.context)
        .load(url)
        .into(container)
}

fun loadImage(resId: Int, container: ImageView) {
    Glide.with(container.context)
        .load(resId)
        .into(container)
}

fun loadImage(uri: Uri, container: ImageView) {
    Glide.with(container.context)
        .load(uri)
        .into(container)
}