package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = RoomStory::class,
            parentColumns = ["id"],
            childColumns = ["storyId"]
        )],
    indices = [
        Index("storyId")
    ]
)
class RoomPlayer(
    @PrimaryKey
    val id: Long,
    val name: String,
    val characterId: Long,
    val storyId: Long
)