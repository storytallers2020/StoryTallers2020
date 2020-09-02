package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel
import ru.storytellers.utils.*
import java.util.*


class LevelViewModel : BaseViewModel<DataModel>() {
    private val app = StoryTallerApp.instance
    private val storage = app.gameStorage
    private val levelLiveData = MutableLiveData<Int>()

    fun subscribeOnChangeLevel(): LiveData<Int> {
        return levelLiveData
    }

    fun setLevelGame(levelId: Int) {
        storage.level = app.levels.getLevelById(levelId)
        levelLiveData.value = levelId
    }

    fun getLevelGame() = StoryTallerApp.instance.gameStorage.level?.id ?: 0

    fun isPlayerListNotEmpty() = StoryTallerApp.instance.gameStorage.getPlayers().isNotEmpty()

    fun onNextScreen() {
        val prop = listOf(
            Pair(StatHelper.levelName, storage.level?.id.toString()),
            Pair(StatHelper.selectLevelTime, getCurrentDateTime().getString())
        )
        app.stat.riseEvent(StatHelper.levelScreenNextClicked, prop.toProperties())
    }

}