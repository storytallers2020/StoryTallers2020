package ru.storytellers.model.datasource.room

import android.content.Context
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ICashImageDataSource
import ru.storytellers.utils.cashImage
import java.io.File

class CashImageDataSource(private val context: Context, private val dir: File)
    : ICashImageDataSource {

    override fun add(urlList: List<String>) {
        urlList.map {
            cashImage(it, context, dir)
        }
    }
}