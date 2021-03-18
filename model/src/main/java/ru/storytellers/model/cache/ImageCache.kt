package ru.storytellers.model.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.entity.room.RoomCachedImage
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.toMD5
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageCache(private val database: AppDatabase, private val dir: File) : IImageCache {

    override fun getBytes(url: String): Maybe<ByteArray?> = Maybe.fromCallable {
        database.imageDao.findByUrl(url)?.let {
            File(it.localPath).inputStream().readBytes()
        }
    }.subscribeOn(Schedulers.io())

    override fun cacheImage(url: String, context: Context) {
        Glide.with(context)
            .asDrawable()
            .load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val image = resource.toBitmap()
                    try {
                        Timber.d("Сохранение изображения $url на устройстве")
                        saveImage(url, image.toByteArray()).subscribe({
                            Timber.d("loadInto Image saved")
                        }, {
                            Timber.e(it)
                        })
                    } catch (t: Throwable) {
                        Timber.e(t, "Ошибка сохранения картинок на устройстве")
                    }
                }
            })
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.use { it.toByteArray() }
    }

    private val png = ".png"
    fun saveImage(url: String, bytes: ByteArray): Completable =
        Completable.create { emitter ->
            if (!dir.exists() && !dir.mkdirs()) {
                throw IOException("Failed to create cache! Dir: ${dir.absolutePath}")
            }

            val imageFile = File(dir, url.toMD5() + png)
            Timber.d("$dir ${url.toMD5()} + $png")

            FileOutputStream(imageFile).use { stream ->
                stream.write(bytes)
            }
            Timber.d("saveImage: image saved. Try to add image info to database")
            database.imageDao.insert(RoomCachedImage(url,imageFile.path))
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
}