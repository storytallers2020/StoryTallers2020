package ru.storytellers.utils

import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.ImageType
import ru.storytellers.model.entity.room.RoomCharacter

fun Character.toRoomCharacter() =
    RoomCharacter(
        this.id,
        this.name,
        this.avatarUrl,
        this.avatarUrlSelected
    )

fun RoomCharacter.toCharacter() =
    Character(
        this.id,
        this.name,
        this.avatarUrl,
        this.avatarUrlSelected
    )

fun List<RoomCharacter>.toCharacterList() = this.map { it.toCharacter() }

fun List<Character>.toRoomCharacterList() = this.map { it.toRoomCharacter() }

fun List<Character>.toAvatarList(): List<String> {
    val list = ArrayList<String>()

    this.map {
        list.add(it.avatarUrl)
        list.add(it.avatarUrlSelected)
    }
    return list
}

