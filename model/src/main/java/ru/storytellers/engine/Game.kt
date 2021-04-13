package ru.storytellers.engine

import ru.storytellers.engine.level.Level
import ru.storytellers.model.datasource.storage.WordStorage
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.getPlayerNumByTurn

class Game(private val wordStorage: WordStorage) {

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

    fun nextStep(sentenceOfTale: SentenceOfTale, currentWord: String): Boolean {
        val res = level
            .rules
            .isSentenceCorrect(sentenceOfTale.content)

        val wordRes =
            if (level.wordRule.isNeedUseWord())
                wordStorage.checkWordExists(sentenceOfTale.content, currentWord)
            else true

        if (res && wordRes) turn++

        return res && wordRes
    }

}