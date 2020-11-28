package ru.storytellers.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.storytellers.model.cache.IImageCache
import timber.log.Timber
import java.io.ByteArrayOutputStream


fun downloadImage(url: String, context: Context, imageCache: IImageCache) {
    Glide.with(context)
        .asDrawable()
        .load(url)
        .into(object : CustomTarget<Drawable>(){
            override fun onLoadCleared(placeholder: Drawable?) { }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                val image = resource.toBitmap()
                try {
                    Timber.d("Сохранение изображения $url на устройстве")
                    imageCache.saveImage(url, image.toByteArray()).subscribe({
                        Timber.d("loadInto Image saved")
                    }, {
                        Timber.e(it)
                    })
                } catch(t: Throwable) {
                    Timber.e(t, "Ошибка сохранения картинок на устройстве")
                }
            }
        })
}

//TODO: Вынести в helper?
private fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.use { it.toByteArray() }
}