package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ICashImageDataSource
import ru.storytellers.model.entity.room.RoomCachedImage
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.toMD5
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CashImageDataSource(private val database: AppDatabase, private val dir: File) :
    ICashImageDataSource {

    private val Png = ".png"

    override fun add(urlList: List<String>): Completable =
        Completable.create { emitter ->
            urlList.map {
                val image = getRemoteImage(it).toByteArray()
                try {
                    saveImage(it, image)
                } catch(t: Throwable) {
                    Timber.e(t, "Ошибка сохранения картинок на устройстве")
                }
            }
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())

    //TODO: Переместить в ImageHelper
    private fun saveImage(url: String, bytes: ByteArray) {
            if (!dir.exists() && !dir.mkdirs()) {
                throw IOException("Failed to create cache! Dir: ${dir.absolutePath}")
            }

            val imageFile = File(dir, url.toMD5() + Png)
            Timber.d("$dir ${url.toMD5()} + $Png")

            FileOutputStream(imageFile).use { stream ->
                stream.write(bytes)
            }
            Timber.d("saveImage: image saved. Try to add image info to database")

            database.imageDao.insert(RoomCachedImage(url, imageFile.path))
            Timber.d("saveImage: image info added to database")
        }

}