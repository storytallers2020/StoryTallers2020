package ru.storytellers.engine.rules

interface IRule {
    fun isCorrect(str: String): Boolean
}
