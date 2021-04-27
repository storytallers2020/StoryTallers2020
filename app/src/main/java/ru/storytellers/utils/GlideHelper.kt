package ru.storytellers.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


//fun loadImage(url: String, container: ImageView) {
//    Glide.with(container.context)
//        .load(url)
//        .into(container)
//}
//

//fun loadImage(resId: Int, container: ImageView) {
//    Glide.with(container.context)
//        .load(resId)
//        .into(container)
//}


fun loadImage(resId: Int, container: View) {
    Glide.with(container.context)
        .load(resId)
        .into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                container.background = resource
            }
        })
}

@Deprecated(
    message = "Используйте класс imageLoader и интерфейс IImageLoader",
    replaceWith = ReplaceWith("imageLoader.loadInto()")
)
fun loadImage(uri: Uri, container: View) {
    Glide.with(container.context)
        .asDrawable()
        .load(uri)
        .into(object : CustomTarget<Drawable>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable>?
            ) {
                container.background = resource
            }
        })
}
