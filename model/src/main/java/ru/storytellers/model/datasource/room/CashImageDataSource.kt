package ru.storytellers.model.datasource.room

import android.content.Context
import ru.storytellers.model.cache.ICashImageDataSource
import ru.storytellers.model.cache.IImageCache

class CashImageDataSource(
    private val context: Context,
    private val cache: IImageCache
    ) : ICashImageDataSource {

    override fun add(urlList: List<String>) {
        urlList.map {
            cache.cacheImage(it, context)
        }
    }
}