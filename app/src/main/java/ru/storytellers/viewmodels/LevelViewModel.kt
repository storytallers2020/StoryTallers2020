package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.getNameLevel
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel


class LevelViewModel : BaseViewModel<DataModel>() {
    private val app = StoryHeroesApp.instance
    private val storage = app.gameStorage
    private val levelLiveData = MutableLiveData<Int>()

    fun subscribeOnChangeLevel(): LiveData<Int> {
        return levelLiveData
    }

    fun setLevelGame(levelId: Int) {
        storage.level = app.levels.getLevelById(levelId)
        levelLiveData.value = levelId
    }

    fun getLevelGame() = storage.level?.id ?: 0

    fun isPlayerListNotEmpty() = StoryHeroesApp.instance.gameStorage.getPlayers().isNotEmpty()

    fun onNextScreen() {
        val prop = listOf(
            StatHelper.levelName to getNameLevel(storage.level!!.id),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.levelScreenBtnToCharacterScreen, prop)
    }




}