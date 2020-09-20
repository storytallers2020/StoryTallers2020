package ru.storytellers.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomLocation(
    @PrimaryKey
    val id: Long,
    val name: String,
    val imageUrl: String,
    val imageForRecycler: String,
    val description: String


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoomLocation

        if (id != other.id) return false
        if (name != other.name) return false
        if (imageUrl != other.imageUrl) return false
        if (imageForRecycler != other.imageForRecycler) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + imageForRecycler.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}