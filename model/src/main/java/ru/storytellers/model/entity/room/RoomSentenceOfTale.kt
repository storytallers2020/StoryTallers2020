package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomStory::class,
        parentColumns = ["id"],
        childColumns = ["storyId"]
    )]
)
class RoomSentenceOfTale (
    @PrimaryKey
    val id: Long,
    val storyId: Long,
    val characterId: Long,
    val step: Int,
    val content: String,
    val contentType: String
)