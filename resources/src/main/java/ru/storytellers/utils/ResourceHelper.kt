package ru.storytellers.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

fun resourceToUri(context: Context, resId: Int): Uri? =
    Uri.parse(resourceToString(context, resId))

fun resourceToUri(url: String): Uri? =
    Uri.parse(url)

fun resourceToString(context: Context, resId: Int): String =
    ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
            context.resources.getResourcePackageName(resId) + '/' +
            context.resources.getResourceTypeName(resId) + '/' +
            context.resources.getResourceEntryName(resId)
