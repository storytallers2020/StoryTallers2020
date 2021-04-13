package ru.storytellers.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val defaultDateFormat = "yyyy.MM.dd HH:mm:ss"
const val statisticTimeFormat = "mm:ss"
@SuppressLint("ConstantLocale")
private val localeDefault = Locale.getDefault()

fun Date.getString(
    format: String = defaultDateFormat,
    locale: Locale = localeDefault
): String = SimpleDateFormat(format, locale).format(this)

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun timeMillisToDate(timeMillis:Long)=Date(timeMillis)
fun Date.getStringForStatistics(
    format: String = statisticTimeFormat,
    locale: Locale = localeDefault
): String = SimpleDateFormat(format, locale).format(this)

 fun timeFromGameCreation(timeCreationGame:Long)=
     timeMillisToDate(getCurrentDateTime().time-timeCreationGame)

