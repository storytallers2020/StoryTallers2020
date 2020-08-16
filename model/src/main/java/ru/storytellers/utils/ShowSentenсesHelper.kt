package ru.storytellers.utils

import java.lang.StringBuilder

fun Char.isDashSymbol(): Boolean =
    when (this) {
        '-' -> true
        else -> false
    }

fun String.isStartFromDash(): Boolean =
    this.isNotEmpty() && this[0].isDashSymbol()

// Вот эту функцию вызывать для сбора прделожений в "красивый текст"
fun List<String>.collectSentence(): String {
    var text = ""
    this.forEach { sentence ->
        text +=
            if (sentence.isStartFromDash()) {
                "\r\n$sentence\r\n"
            } else {
                sentence.addSpaceAfterPunctuationSymbol()
            }
    }
    return text
}

// Получить предыдущее предложение (2й и 3й уровни)
fun List<String>.getPrevSentences(turn: Int): String {
    if (turn < 2) return ""
    return this[turn-1-1]
}

fun String.addSpaceAfterPunctuationSymbol(): String {
    val newStr = StringBuilder()
    var index = 0
    while (index < this.count()) {
        if (this[index].isEndSentencePunctuationSymbol()) {
            newStr.append("${this[index]} ")
        } else {
            newStr.append(this[index])
        }
        index++
    }
    return newStr.toString()
}
