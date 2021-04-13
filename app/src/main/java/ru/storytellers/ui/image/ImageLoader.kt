package ru.storytellers.ui.image

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.cache.IImageCache
import ru.storytellers.model.image.IImageLoader
import timber.log.Timber

class ImageLoader(
    override val cache: IImageCache
) : IImageLoader {

    override fun loadInto(url: String, defaultResource: Int, container: ImageView) {
        cache.getBytes(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ byteArray ->
                Glide.with(container.context)
                    .asBitmap()
                    .error(defaultResource)
                    .load(byteArray)
                    .into(container)
            }, {
                Timber.e(it)
            })
    }

    override fun loadBackground(url: String, container: View) {
        cache.getBytes(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ byteArray ->

                Glide.with(container.context)
                    .load(byteArray)
                    .into(object : CustomTarget<Drawable>() {
                        override fun onLoadCleared(placeholder: Drawable?) {}
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            container.background = resource
                        }
                    })

            }, {
                Timber.e(it)
            })
    }
}
