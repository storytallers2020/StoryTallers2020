package ru.storytellers.model.entity.room

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


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoomStory

        if (id != other.id) return false
        if (name != other.name) return false
        if (authors != other.authors) return false
        if (coverUrl != other.coverUrl) return false
        if (locationId != other.locationId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + authors.hashCode()
        result = 31 * result + coverUrl.hashCode()
        result = 31 * result + locationId.hashCode()
        return result
    }
}