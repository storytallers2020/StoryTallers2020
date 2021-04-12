package ru.storytellers.ui.image

import android.widget.ImageView
import com.bumptech.glide.Glide
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
}
