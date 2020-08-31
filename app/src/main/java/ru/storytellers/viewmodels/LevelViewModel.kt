package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel


class LevelViewModel : BaseViewModel<DataModel>() {
    private val levelLiveData = MutableLiveData<Int>()

    fun subscribeOnChangeLevel(): LiveData<Int> {
        return levelLiveData
    }

    fun setLevelGame(levelId: Int) {
        with(StoryTallerApp.instance) {
            gameStorage.level = levels.getLevelById(levelId)
        }
        levelLiveData.value = levelId
    }

    fun getLevelGame() = StoryTallerApp.instance.gameStorage.level?.id ?: 0

    fun isPlayerListNotEmpty() = StoryTallerApp.instance.gameStorage.getPlayers().isNotEmpty()

}