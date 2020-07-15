package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.storytellers.model.entity.ContentTypeEnum

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
    val playerId: Long,
    val turn: Int,
    val content: String,
    val contentType: ContentTypeEnum
)