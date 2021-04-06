package ru.storytellers.engine.wordRules

import ru.storytellers.model.datasource.storage.WordStorage

class RandomWordRule(
    private val needShowWord: Boolean
) : IWordRule {

    private var currentWord: String = ""

    override fun isNeedUseWord(): Boolean = needShowWord

//    override fun getRandomWord(): String {
//        currentWord = wordStorage.getRandomWord()
//        return currentWord
//    }

//    fun getRandomWord(): String {
//        val index = Random.nextInt(0, wordList.size - 1)
//        return wordList[index]
//    }

//    override fun checkWordExists(sentenceText: String): Boolean =
//        if (needShowWord) sentenceText.contains(currentWord, true)
//        else true
}