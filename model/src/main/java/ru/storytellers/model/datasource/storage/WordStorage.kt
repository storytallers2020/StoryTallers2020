package ru.storytellers.model.datasource.storage

import kotlin.random.Random

class WordStorage {

    private val words = mutableListOf<String>()

    fun setWords(wordList: List<String>) {
        words.clear()
        words.addAll(wordList)
    }

    fun getRandomWord(): String {
        val index = Random.nextInt(0, words.size - 1)
        return words[index]
    }

    fun checkWordExists(sentenceText: String, currentWord: String): Boolean =
        sentenceText.contains(currentWord, true)

}