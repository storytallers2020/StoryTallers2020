package ru.storytellers.engine

import ru.storytellers.engine.level.Level
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.SentenceOfTale

class Game() {

    lateinit var level: Level
    private lateinit var players: List<Character>
    private var turn: Int = 0

    fun getCurrentPlayer(): Character =
        if (turn <= players.count()) players[turn - 1]
        else players[turn - turn / players.count()]

    fun newGame(players: List<Character>, level: Level) {
        turn = 0

        this.players = players
        this.level = level
    }

    fun nextStep(sentenceOfTale: SentenceOfTale): Boolean {
        val res = level
            .rules
            .isSentenceCorrect(sentenceOfTale.content)

        return if (res) {
            turn++
            true
        } else
            false
    }

}