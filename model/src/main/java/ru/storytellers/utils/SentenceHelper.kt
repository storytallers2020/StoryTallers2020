package ru.storytellers.utils

import java.lang.StringBuilder

fun String.trimSentenceSpace() = this.trim()

fun String.isLastPunctuationSymbol() =
    this.last().isPunctuationSymbol()

fun Char.isPunctuationSymbol() =
    when (this) {
        '.' -> true
        '!' -> true
        '?' -> true
        else -> false
    }

fun String.addDotIfNeed(): String =
    if (this.isLastPunctuationSymbol()) this
    else "$this."

fun List<String>.collectSentence(): String {
    var text = ""
    this.forEachIndexed() { index, sentence ->
        text +=
            if (index < this.count() - 1) "$sentence\r\n"
            else sentence
    }
    return text
}

fun String.addSpaceAfterPunctuationSymbol(): String {
    val newStr = StringBuilder()
    var index = 0
    while (index < this.count()) {
        if (this[index].isPunctuationSymbol()) {
            newStr.append("${this[index]} ")
        } else {
            newStr.append(this[index])
        }
        index++
    }
    return newStr.toString()
}


