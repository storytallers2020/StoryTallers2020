package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomUser (
    @PrimaryKey
    val id: String,
    val nickName: String,
    val login: String,
    val pass: String,
    val email: String,
    val avatarUrl: String
)