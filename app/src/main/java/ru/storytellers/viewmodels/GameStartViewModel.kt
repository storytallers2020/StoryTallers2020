package ru.storytellers.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.model.DataModel
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.utils.toProperties
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameStartViewModel(private val game: Game) : BaseViewModel<DataModel>() {
    private val app = StoryTallerApp.instance
    private val storage = app.gameStorage
    private val levelGameLiveData = MutableLiveData<Int>()

    fun requestGameLevelFromStorage() {
        levelGameLiveData.value = StoryTallerApp.instance.gameStorage.level?.id ?: 0
    }

    fun subscribeOnLevelGame() = levelGameLiveData

    fun createNewGame() {
        storage.level?.let { level ->
            game.newGame(storage.getPlayers(), level)
        }

    }

    fun buttonStartClickedStatistic() {
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.gameStartScreenBtnStartClicked, prop)
    }

}