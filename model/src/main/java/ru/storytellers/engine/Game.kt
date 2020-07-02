package ru.storytellers.engine

import ru.storytellers.engine.rules.Rules
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.SentenceOfTale

class Game() {

    lateinit var rules: Rules
    private lateinit var players: List<Character>
    private var turn: Int = 0

    fun getCurrentPlayer(): Character =
        if (turn <= players.count()) players[turn - 1]
        else players[turn - turn / players.count()]

    fun newGame(players: List<Character>, rules: Rules) {
        turn = 0

        this.players = players
        this.rules = rules
    }

    fun nextStep(sentenceOfTale: SentenceOfTale): Boolean =
        if (rules.isSentenceCorrect(sentenceOfTale.content)) {
            turn++
            true
        } else
            false

}