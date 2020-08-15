package ru.storytellers.engine.wordRules

interface IWordRule {
    fun isNeedUseWord(): Boolean
    fun getRandomWord(): String
    fun checkWordExists(sentenceText: String): Boolean
}
