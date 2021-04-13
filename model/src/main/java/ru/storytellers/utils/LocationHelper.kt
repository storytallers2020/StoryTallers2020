package ru.storytellers.utils

import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.room.RoomLocation

fun Location.toRoomLocation() =
    RoomLocation(
        this.id,
        this.name,
        this.imageUrl,
        this.imageForRecycler,
        this.descriptions
    )

fun RoomLocation.toLocation() =
    Location(
        this.id,
        this.name,
        this.imageUrl,
        this.imageForRecycler,
        this.description
    )

fun List<RoomLocation>.toLocationList() = this.map { it.toLocation() }

fun List<Location>.toRoomLocationList() = this.map { it.toRoomLocation() }

fun List<Location>.toCashedLocationList(): List<String> {
    val list = ArrayList<String>()

    this.map {
        list.add(it.imageUrl)
        list.add(it.imageForRecycler)
    }
    return list
}
