package ru.storytellers.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.model.DataModel
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.utils.toProperties
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameStartViewModel(private val game: Game) : BaseViewModel<DataModel>() {
    private val storage = StoryTallerApp.instance.gameStorage
    private val levelGameLiveData = MutableLiveData<Int>()

    fun requestGameLevelFromStorage() {
        levelGameLiveData.value = StoryTallerApp.instance.gameStorage.level?.id ?: 0
    }

    fun subscribeOnLevelGame() = levelGameLiveData

    fun createNewGame() {
        storage.level?.let { level ->
            game.newGame(storage.getPlayers(), level)
        }
        gameStartStatistic()
    }

    private fun gameStartStatistic() {
        val level = when (storage.level?.id) {
            0 -> "Easy"
            1 -> "Medium"
            2 -> "Hard"
            else -> "level not selected"
        }
        val prop = listOf(
            StatHelper.numberOfPlayersGame to storage.getPlayers().count().toString(),
            StatHelper.selectedLevelGame to level,
            StatHelper.createGameTime to getCurrentDateTime().getString()
        )
        stat.riseEvent(StatHelper.onGameStartScreen, prop.toProperties())
    }

    fun buttonStartClickedStatistic() {
        stat.riseEvent(StatHelper.buttonStartClicked)
    }

}