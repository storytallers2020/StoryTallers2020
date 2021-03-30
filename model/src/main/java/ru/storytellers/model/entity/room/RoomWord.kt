package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomWord(
    @PrimaryKey
    val id: Long,
    val word: String,
    val lang: String,
)