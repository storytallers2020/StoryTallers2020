package ru.storytellers.utils

import kotlin.reflect.full.declaredMemberProperties

// Вывод полей в строку для записи в лог
fun Any.fieldsToLogString(): String {
    var logString = ""

    val clazz = this.javaClass.kotlin.declaredMemberProperties
    clazz.forEach { field ->
        logString += try {
            "${field.name}: [${field.get(this)}], "
        } catch (e: Exception) {
            "[can't get some name or value of field], "
        }
    }

    return logString
}
