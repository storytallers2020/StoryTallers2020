package ru.storytellers.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.utils.toMD5
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageCache(private val dir: File): IImageCache {
    override fun contains(url: String): Single<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getBytes(url: String): Maybe<ByteArray?> {
        TODO("Not yet implemented")
    }

    override fun saveImage(url: String, bytes: ByteArray): Completable {
        Completable.create { emitter ->
            if (!dir.exists() && !dir.mkdirs()) {
                emitter.onError(IOException("Failed to create cache! Dir: ${dir.absolutePath}"))
                return@create
            }


            val imageFile = File(dir, url.toMD5() + "PNG")
            imageFile.name
            Timber.d(imageFile.name)
            try {
                FileOutputStream(imageFile).use { stream ->
                    stream.write(bytes)
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
            Timber.d("saveImage image saved. Try to add image info to database")
            //TODO: Это пример из клиента VK. У меня планируется немного по другому. Но возможно нужно как здесь.
            database.imageDao.insert(RoomCachedImage(url,imageFile.path)
            )
            Timber.d("saveImage image info added to database")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
    }

}