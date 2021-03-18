package ru.storytellers.model.image

import android.widget.ImageView
import ru.storytellers.model.cache.IImageCache

interface IImageLoader {
    val cache: IImageCache
    fun loadInto(url: String, defaultResource: Int, container: ImageView)
}