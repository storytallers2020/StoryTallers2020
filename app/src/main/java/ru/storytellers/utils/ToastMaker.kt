package ru.storytellers.utils

import android.content.Context
import android.widget.Toast

fun toastShowLong(context: Context?, message: String?) {
    context?.let {
        Toast.makeText(context, message ?: "", Toast.LENGTH_LONG).show()
    }
}

fun toastShowShort(context: Context?, message: String?) {
    context?.let {
        Toast.makeText(context, message ?: "", Toast.LENGTH_SHORT).show()
    }
}