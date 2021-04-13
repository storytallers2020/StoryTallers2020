package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomVersions (
    @PrimaryKey
    val id: Int,
    val characterVersion: Int,
    val locationVersion: Int,
    val coverVersion: Int,
    val rusWordVersion: Int,
    val engWordVersion: Int,
    val thirdLevelWordVersion: Int
)

