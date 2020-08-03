package ru.storytellers.utils

import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale

fun List<SentenceOfTale>.getPlayersFromSentences(): List<Player> {
    val playerList = ArrayList<Player>()
    this.forEach {sentence ->
        val res = playerList.firstOrNull { it.id == sentence.player.id }
        if (res == null) playerList.add(sentence.player)
    }
    return playerList
}
