package ru.storytellers.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import timber.log.Timber

fun String.setTextToClipboard(context: Context): Boolean {
    try {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", this)
        clipboard.setPrimaryClip(clip)
    } catch (e: Exception) {
        Timber.e(e)
        return false
    }

    return true
}