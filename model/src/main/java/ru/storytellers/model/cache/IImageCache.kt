package ru.storytellers.model.cache

import android.content.Context
import io.reactivex.rxjava3.core.Maybe

interface IImageCache {
    fun getBytes(url: String): Maybe<ByteArray?>
    fun cacheImage(url: String, context: Context)
}