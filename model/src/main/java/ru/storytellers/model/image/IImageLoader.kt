package ru.storytellers.model.image

import android.view.View
import android.widget.ImageView
import ru.storytellers.model.cache.IImageCache

interface IImageLoader {
    val cache: IImageCache
    fun loadInto(url: String, defaultResource: Int, container: ImageView)
    fun loadBackground(url: String, container: View)
}