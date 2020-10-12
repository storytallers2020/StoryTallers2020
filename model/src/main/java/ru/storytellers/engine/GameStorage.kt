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
    }

    fun getCoverStoryTaller() = cover

    fun setCoverStoryTaller(coverStory: Cover) {
        cover = coverStory
    }

    fun getPlayers() = playerList

    fun getSentences() = sentenceOfTaleList

    fun getLocationGame() = location

    fun setLocationGame(location: Location) {
        this.location = location
    }

}