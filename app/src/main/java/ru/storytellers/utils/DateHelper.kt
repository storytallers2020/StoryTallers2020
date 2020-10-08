package ru.storytellers.utils

import java.text.SimpleDateFormat
import java.util.*

const val defaultDateFormat = "yyyy.MM.dd HH:mm:ss"
const val statisticTimeFormat = "mm:ss"
private val localeDefault = Locale.getDefault()

fun Date.getString(
    format: String = defaultDateFormat,
    locale: Locale = localeDefault
): String = SimpleDateFormat(format, locale).format(this)

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun timeMillisToDate(timeMillis:Long)=Date(timeMillis)