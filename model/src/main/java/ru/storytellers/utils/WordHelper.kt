package ru.storytellers.utils

import ru.storytellers.model.entity.room.RoomWord

fun String.toRoomWord(lang: String) =
    RoomWord(
        getUid(),
        this,
        lang
    )

fun List<RoomWord>.toWordList() = this.map { it.word }

fun List<String>.toRoomWordList(lang: String) = this.map { it.toRoomWord(lang) }