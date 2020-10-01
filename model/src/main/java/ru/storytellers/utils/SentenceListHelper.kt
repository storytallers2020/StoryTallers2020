package ru.storytellers.utils

import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.room.RoomSentenceOfTale

fun List<SentenceOfTale>.getSortedList(): List<SentenceOfTale> =
    this.sortedBy { it.step }

fun List<SentenceOfTale>.toListOfStrings(): List<String> {
    val list = ArrayList<String>()
    this.forEach {
            list.add(it.content)
        }
    return list
}

fun List<SentenceOfTale>.toRoomSentences(storyId: Long): List<RoomSentenceOfTale> =
    this.map { it.toRoomSentence(storyId) }
