package ru.storytallers.utils

// Вывод полей в строку для записи в лог
fun Unit.fieldsToLogString(): String {
    var logString = ""

    val fields = this::class.java.declaredFields
    fields.forEach {field ->
        try {
            logString += "${field.name}: ${field.get(this)} "
        } catch (e: Exception) {
            logString += "[can't get some name or value of field],\r\n "
        }
    }

    return logString
}