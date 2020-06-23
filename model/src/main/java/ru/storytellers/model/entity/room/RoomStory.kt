package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomStory (
    @PrimaryKey
    val id: String,
    val name: String,
    val authors: String,
    val coverUrl: String,
    val locationId: String
)