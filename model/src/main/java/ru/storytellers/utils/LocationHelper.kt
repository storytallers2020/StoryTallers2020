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