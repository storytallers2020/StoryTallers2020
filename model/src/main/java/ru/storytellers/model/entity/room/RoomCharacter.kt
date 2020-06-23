package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomCharacter (
    @PrimaryKey
    val id: String,
    val name: String,
    val avatarUrl: String
)