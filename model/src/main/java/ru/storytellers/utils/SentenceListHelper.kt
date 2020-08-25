package ru.storytellers.utils

import ru.storytellers.model.entity.Player
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
    this.map { it.toRoomSentenceOfTale(storyId) }

fun SentenceOfTale.toRoomSentenceOfTale(storyId: Long): RoomSentenceOfTale =
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