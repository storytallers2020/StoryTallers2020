package ru.storytellers.utils

import java.lang.StringBuilder

// Вызвать перед сохранением предложения, для убирания лишних пробелов
fun String.trimSentenceSpace() = this.trim()

fun String.isLastPunctuationSymbol() =
    this.last().isEndSentencePunctuationSymbol()

fun Char.isEndSentencePunctuationSymbol() =
    when (this) {
        '.' -> true
        '!' -> true
        '?' -> true
        else -> false
    }

// Вызвать перед сохранением предложения, для добавления точки, если игрок ничего не поставил в конце
fun String.addDotIfNeed(): String =
    if (this.isLastPunctuationSymbol()) this
    else "$this."


