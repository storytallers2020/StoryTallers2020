package ru.storytellers.utils

import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.ImageType
import ru.storytellers.model.entity.room.RoomCover

fun Cover.toRoomCover() =
    RoomCover(
        this.id,
        this.name,
        this.imageUrl,
        this.imagePreview
    )

fun RoomCover.toCover() =
    Cover(
        this.id,
        this.name,
        this.imageUrl,
        this.imagePreview
    )

fun List<RoomCover>.toCoverList() = this.map { it.toCover() }

fun List<Cover>.toRoomCoverList() = this.map { it.toRoomCover() }

fun List<Cover>.toCachedCoverList(): List<String> {
    val list = ArrayList<String>()

    this.map {
        list.add(it.imagePreview)
        list.add(it.imageUrl)
    }
    return list
}