package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomUser (
    @PrimaryKey
    val id: Long,
    val name: String,
    val email: String,
    val avatarUrl: String
)