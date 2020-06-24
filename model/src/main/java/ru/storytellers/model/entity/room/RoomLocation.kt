package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomLocation (
    @PrimaryKey
    val id: Long,
    val imageUrl: String,
    val description: String
)