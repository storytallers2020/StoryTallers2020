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

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoomSentenceOfTale

        if (id != other.id) return false
        if (storyId != other.storyId) return false
        if (playerId != other.playerId) return false
        if (turn != other.turn) return false
        if (content != other.content) return false
        if (contentType != other.contentType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + storyId.hashCode()
        result = 31 * result + playerId.hashCode()
        result = 31 * result + turn
        result = 31 * result + content.hashCode()
        result = 31 * result + contentType.hashCode()
        return result
    }
}