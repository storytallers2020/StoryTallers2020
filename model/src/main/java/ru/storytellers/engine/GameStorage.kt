package ru.storytellers.engine

import ru.storytellers.engine.level.Level
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale

class GameStorage {
    private val playerList = mutableListOf<Player>()
    var location: Location? = null
    private val sentenceOfTaleList = mutableListOf<SentenceOfTale>()
    private var cover: Cover? = null
    private var title: String? = null
    private var timeCreateStory: Long = 0
    private var gameEnded: Int =
        0  //  needed to let players resume the game only once after ending it

    var level: Level? = null

    fun getTimeCreateStory() = timeCreateStory
    fun setTimeCreateStory(timeCreate: Long) {
        timeCreateStory = timeCreate
    }

    fun getTitleStory() = title

    fun setTitleStory(titleStory: String) {
        title = titleStory
    }

    fun clear() {
        title = null
        cover = null
        playerList.clear()
        location = null
        sentenceOfTaleList.clear()
        level = null
        timeCreateStory = 0
        gameEnded = 0
    }

    fun getCoverStory() = cover

    fun setCoverStory(coverStory: Cover) {
        cover = coverStory
    }

    fun getPlayers() = playerList

    fun getSentences() = sentenceOfTaleList

    fun getLocationGame() = location

    fun setLocationGame(location: Location) {
        this.location = location
    }

    fun getGameEnded() = gameEnded

    fun setGameEnded() {
        gameEnded++
    }

}