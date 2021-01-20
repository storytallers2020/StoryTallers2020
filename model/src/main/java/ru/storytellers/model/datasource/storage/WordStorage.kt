package ru.storytellers.model.datasource.storage

import android.content.Context
import ru.storytellers.resources.R
import kotlin.random.Random

class WordStorage(context: Context) {

    private var wordList : MutableList<String>

    init {
        val str = context.getString(R.string.word)
        wordList = str.lines().toMutableList()
    }

    fun getRandomWord(): String {
        val index = Random.nextInt(0, wordList.size - 1)
        return wordList[index]
    }

    fun getAll(): List<String> = wordList
}