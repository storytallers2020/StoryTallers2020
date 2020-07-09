package ru.storytellers.engine

import ru.storytellers.engine.level.Level
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.getPlayerNumByTurn

class Game() {

    lateinit var level: Level
    private lateinit var players: List<Character>
    private var turn: Int = 1

    fun getCurrentPlayer(): Character {
        val playerNum = getPlayerNumByTurn(turn, players.count())
        return players[playerNum-1]
    }

    fun newGame(players: List<Character>, level: Level) {
        turn = 1

        this.players = players
        this.level = level
    }

    fun nextStep(sentenceOfTale: SentenceOfTale): Boolean {
        val res = level
            .rules
            .isSentenceCorrect(sentenceOfTale.content)

        if (res) turn++

        return res
    }

}