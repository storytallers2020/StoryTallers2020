package ru.storytellers.utils

import java.lang.StringBuilder

//fun List<String>.collectSentence(): String {
//    var text = ""
//    this.forEachIndexed() { index, sentence ->
//        text +=
//            if (index < this.count() - 1) "$sentence\r\n"
//            else sentence
//    }
//    return text
//}

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
    this.forEachIndexed() { index, sentence ->
        text +=
            if (sentence.isStartFromDash()) {
                "\r\n$sentence\r\n"
            } else {
                sentence.addSpaceAfterPunctuationSymbol()
            }
    }
    return text
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
