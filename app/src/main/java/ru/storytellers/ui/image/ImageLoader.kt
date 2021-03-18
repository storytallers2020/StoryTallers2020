package ru.storytellers.ui.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.cache.IImageCache
import ru.storytellers.model.image.IImageLoader
import ru.storytellers.model.network.INetworkStatus
import timber.log.Timber

class ImageLoader(
    override val cache: IImageCache,
    private val networkStatus: INetworkStatus
) : IImageLoader {

    override fun loadInto(url: String, defaultResource: Int, container: ImageView) {
        networkStatus.isOnlineSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isOnline ->
                if (isOnline) {
                    Glide.with(container.context)
                        .load(url)
                        .into(container)
                } else {
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
    }
}