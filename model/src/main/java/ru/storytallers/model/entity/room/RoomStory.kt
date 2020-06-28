package ru.storytallers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomStory (
    @PrimaryKey
    val id: Long,
    val name: String,
    val authors: String,
    val coverUrl: String,
    val locationId: Long
)