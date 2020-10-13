package ru.storytellers.engine

import ru.storytellers.engine.level.Level
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.getPlayerNumByTurn

class Game {

    lateinit var level: Level
    private lateinit var players: List<Player>
    var turn: Int = 1

    fun getCurrentPlayer(): Player {
        val playerNum = getPlayerNumByTurn(turn, players.count())
        if (playerNum == 0) return players[players.count() - 1]
        return players[playerNum - 1]
    }

    fun newGame(players: List<Player>, level: Level) {
        turn = 1

        this.players = players
        this.level = level
    }

    fun nextStep(sentenceOfTale: SentenceOfTale): Boolean {
        val wordRes =
            if (level.wordRule.isNeedUseWord()) {
                level.wordRule.checkWordExists(sentenceOfTale.content)
            } else
                true

        if (wordRes) turn++

        return wordRes
    }

}