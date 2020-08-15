package ru.storytellers.engine.wordRules

import ru.storytellers.model.datasource.resourcestorage.storage.WordStorage

class RandomWordRule(
    private val needShowWord: Boolean,
    private val wordStorage: WordStorage
) : IWordRule {

    private var currentWord: String = ""

    override fun isNeedUseWord(): Boolean = needShowWord

    override fun getRandomWord(): String {
        currentWord = wordStorage.getRandomWord()
        return currentWord
    }

    override fun checkWordExists(sentenceText: String): Boolean =
        if (needShowWord) sentenceText.contains(currentWord, true)
        else true
}