package ru.storytellers.model.entity

import com.google.gson.annotations.Expose

data class Location(
    @Expose val id: Long,
    @Expose val name: String,
    @Expose val imageUrl: String,
    @Expose val imageForRecycler: String,
    @Expose val descriptions: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (id != other.id) return false
        if (name != other.name) return false
        if (imageUrl != other.imageUrl) return false
        if (imageForRecycler != other.imageForRecycler) return false
        if (descriptions != other.descriptions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + imageForRecycler.hashCode()
        result = 31 * result + descriptions.hashCode()
        return result
    }
}