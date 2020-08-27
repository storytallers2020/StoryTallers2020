package ru.storytellers.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.model.DataModel
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameStartViewModel(private val game: Game) : BaseViewModel<DataModel>() {
    private val storage = StoryTallerApp.instance.gameStorage
    private val levelGameLiveData = MutableLiveData<Int>()

    fun requestGameLevelFromStorage() {
        levelGameLiveData.value = StoryTallerApp.instance.gameStorage.level?.id ?: 0
    }

    fun subscribeOnLevelGame() = levelGameLiveData

    fun createNewGame() {
        storage.level?.let {level ->
            game.newGame(storage.getPlayers(), level)
        }
    }

}