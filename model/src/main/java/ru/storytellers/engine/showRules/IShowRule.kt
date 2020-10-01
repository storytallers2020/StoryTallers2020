package ru.storytellers.engine.showRules

interface IShowRule {
    fun getText(turn: Int, sentenceList: List<String>): String
}
