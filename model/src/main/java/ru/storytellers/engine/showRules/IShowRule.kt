package ru.storytellers.engine.showRules

interface IShowRule {
    fun convert(turn: Int, sentenceList: List<String>): String
}
