package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.datasource.storage.WordStorage
import ru.storytellers.model.repository.IWordRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.getNameLevel
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel


class LevelViewModel(
    private val wordRepository: IWordRepository,
    private val wordStorage: WordStorage
) : BaseViewModel<DataModel>() {
    private val app = StoryHeroesApp.instance
    private val storage = app.gameStorage
    private val levelLiveData = MutableLiveData<Int>()


    fun subscribeOnChangeLevel(): LiveData<Int> {
        return levelLiveData
    }

    fun setLevelGame(levelId: Int) {
        storage.level = app.levels.getLevelById(levelId)
        storage.level?.let {level ->
            if (level.wordRule.isNeedUseWord()) loadWords()
        }
        levelLiveData.value = levelId
    }

    private fun loadWords() {
        wordRepository.getAll("rus")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ wordList ->
                wordStorage.setWords(wordList)
            }, {
                //storage.setWords(toList(R.Strings.words))
                //TODO: Show the Error or load default word list
            })
    }

    fun getLevelGame() = storage.level?.id ?: 0

    fun isPlayerListNotEmpty() = app.gameStorage.getPlayers().isNotEmpty()

    fun onNextScreen() {
        val prop = listOf(
            StatHelper.levelName to getNameLevel(storage.level!!.id),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.levelScreenBtnToCharacterScreen, prop)
    }
}