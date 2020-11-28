package ru.storytellers.utils

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.storytellers.model.entity.room.RoomCachedImage
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun downloadImage(url: String, dir: String) {
    Glide.with(. context)
        .asDrawable()
        .load(url)
        .into(object : CustomTarget<Drawable>(){
            override fun onLoadCleared(placeholder: Drawable?) { }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                val image = resource.toBitmap()
                try {
                    saveImage(url, image)
                } catch(t: Throwable) {
                    Timber.e(t, "Ошибка сохранения картинок на устройстве")
                }
            }
        })
}

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


    Timber.d("saveImage: image info added to database")
}