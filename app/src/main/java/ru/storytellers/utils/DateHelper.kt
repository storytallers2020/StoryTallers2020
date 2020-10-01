package ru.storytellers.utils

import java.text.SimpleDateFormat
import java.util.*

const val defaultDateFormat = "yyyy.MM.dd HH:mm:ss"

fun Date.getString(
    format: String = defaultDateFormat,
    locale: Locale = Locale.getDefault()
): String = SimpleDateFormat(format, locale).format(this)

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}