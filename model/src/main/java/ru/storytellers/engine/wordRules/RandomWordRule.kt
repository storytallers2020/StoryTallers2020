package ru.storytellers.engine.wordRules

class RandomWordRule(
    private val needShowWord: Boolean
) : IWordRule {

    override fun isNeedUseWord(): Boolean = needShowWord
}