package ru.storytellers.utils

import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.room.RoomPlayer

fun List<SentenceOfTale>.getPlayersFromSentences(): List<Player> {
    val playerList = ArrayList<Player>()
    this.forEach {sentence ->
        val res = playerList.firstOrNull { it.id == sentence.player?.id }
        if (res == null && sentence.player != null) playerList.add(sentence.player)
    }
    return playerList
}

fun List<Player>.toRoomPlayers(storyId: Long): List<RoomPlayer> =
    this.map {player -> player.toRoomPlayer(storyId) }

fun Player.toRoomPlayer(storyId: Long) =
    RoomPlayer(
        this.id,
        this.name,
        this.character?.id ?: 0,
        storyId
    )

fun RoomPlayer.toPlayer(character: Character?) =
    Player(
        this.id,
        this.name,
        character
    )


