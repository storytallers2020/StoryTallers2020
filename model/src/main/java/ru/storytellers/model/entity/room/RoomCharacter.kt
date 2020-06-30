package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomCharacter (
    @PrimaryKey
    val id: Long,
    val name: String,
    val avatarUrl: String
)