package ru.storytellers.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.utils.*
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameEndViewModel : BaseViewModel<DataModel>() {
    private val textOfStoryTallerLiveData = MutableLiveData<String>()
    private val backgroundImageUriLiveData = MutableLiveData<String>()
    private val isResumeClickedLiveData = MutableLiveData<Boolean>()
    private val app = StoryHeroesApp.instance
    private val gameStorage = app.gameStorage

    fun subscribeOnTextOfStoryTaller() = textOfStoryTallerLiveData
    fun subscribeOnBackgroundImageUri() = backgroundImageUriLiveData
    fun subscribeOnResumeClicked() = isResumeClickedLiveData

    fun setTextOfStoryTaller() {
        textOfStoryTallerLiveData.value = gameStorage
            .getSentences()
            .getSortedList()
            .toListOfStrings()
            .collectSentence()
    }

    fun getUriBackgroundImage() {
        gameStorage.getLocationGame()?.imageUrl?.let {
            //backgroundImageUriLiveData.value = resourceToUri(it)
            backgroundImageUriLiveData.value = it
        }
    }

    fun getResumeBtnVisibility() {
        isResumeClickedLiveData.value = gameStorage.getGameEnded() >= 1
    }

    fun setResumeClicked() {
        gameStorage.setGameEnded()
    }

    fun buttonSelectCoverClickedStat() {
        buttonClickedStatistic(StatHelper.gameEndScreenBtnSelectCoverClicked)
    }

    fun buttonContinueClickedStat() {
        buttonClickedStatistic(StatHelper.gameEndScreenBtnContinueGameClicked)
    }

    fun buttonCopyClickedStat() {
        buttonClickedStatistic(StatHelper.gameEndScreenBtnCopyClicked)
    }

    private fun buttonClickedStatistic(nameButton: String) {
        val clicked = "Clicked"
        val prop = listOf(
            nameButton to clicked,
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(nameButton, prop)
    }

}