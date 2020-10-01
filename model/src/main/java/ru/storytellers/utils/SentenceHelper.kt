package ru.storytellers.utils

import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.room.RoomSentenceOfTale

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

fun SentenceOfTale.toRoomSentence(storyId: Long): RoomSentenceOfTale =
    RoomSentenceOfTale(
        this.id,
        storyId,
        this.player?.id ?: 0,
        this.step,
        this.content,
        this.contentType
    )

fun RoomSentenceOfTale.toSentence(player: Player?): SentenceOfTale =
    SentenceOfTale(
        this.id,
        player,
        this.turn,
        this.content,
        this.contentType
    )


