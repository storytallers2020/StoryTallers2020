package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomLocation (
    @PrimaryKey
    val id: Long,
    val name: String,
    val imageUrl: String,
    val imageForRecycler: String,
    val description: String
)