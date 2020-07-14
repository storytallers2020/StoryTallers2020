package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomPlayer (
    @PrimaryKey
    val id: Long,
    val name: String,
    val characterId: Long,
    val storyId: Long
)