package ru.storytellers.utils

import ru.storytellers.model.datasource.remote.api.VersionApi
import ru.storytellers.model.entity.Versions
import ru.storytellers.model.entity.room.RoomVersions

fun Versions.toRoomVersion() =
    RoomVersions(
        this.id,
        this.characterVersion,
        this.locationVersion,
        this.coverVersion,
        this.rusWordVersion,
        this.engWordVersion,
        this.thirdLevelWordVersion
    )

fun RoomVersions.toVersions() =
    Versions(
        this.id,
        this.characterVersion,
        this.locationVersion,
        this.coverVersion,
        this.rusWordVersion,
        this.engWordVersion,
        this.thirdLevelWordVersion
    )

fun VersionApi.toVersions() =
    Versions(
        this.id,
        this.characterVersion,
        this.locationVersion,
        this.coverVersion,
        this.rusWordVersion,
        this.engWordVersion,
        this.thirdLevelWordVersion
    )

fun Versions.toVersionApi() =
    VersionApi(
        this.id,
        this.characterVersion,
        this.locationVersion,
        this.coverVersion,
        this.rusWordVersion,
        this.engWordVersion,
        this.thirdLevelWordVersion
    )

fun defaultVersions() = Versions(
    0,
    0,
    0,
    0,
    0,
    0,
    0
)