package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.storytellers.model.entity.ContentTypeEnum

@Entity(
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = RoomStory::class,
            parentColumns = ["id"],
            childColumns = ["storyId"]
        )
    ],
    indices = [
        Index("storyId")
    ]
)
class RoomSentenceOfTale(
    @PrimaryKey
    val id: Long,
    val storyId: Long,
    val playerId: Long,
    val turn: Int,
    val content: String,
    val contentType: ContentTypeEnum
)