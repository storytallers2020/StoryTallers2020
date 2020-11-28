package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ICashImageDataSource
import ru.storytellers.model.entity.room.RoomCachedImage
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.downloadImage
import java.io.File

class CashImageDataSource(private val database: AppDatabase, private val dir: File) :
    ICashImageDataSource {

    override fun add(urlList: List<String>): Completable =
        Completable.create { emitter ->
            urlList.map { url ->
                downloadImage(url, dir)
                database.imageDao.insert(RoomCachedImage(url, imageFile.path))
            }
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
}