package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel


class LevelViewModel : BaseViewModel<DataModel>() {
    private val levelGameLiveData = MutableLiveData<Int>()

    fun subscribeOnLevelGame(): LiveData<Int> {
        return levelGameLiveData
    }

    fun setLevelGame(level: Int) {
        with(StoryTallerApp.instance) {
            gameStorage.level = levels.getLevelById(level)
        }
        getLevelGame()
    }

    fun getLevelGame() {
        levelGameLiveData.value = StoryTallerApp.instance.gameStorage.level?.id ?: 0
    }

    fun listPlayerIsNotEmpty() = StoryTallerApp.instance.gameStorage.getPlayers().isNotEmpty()

}


