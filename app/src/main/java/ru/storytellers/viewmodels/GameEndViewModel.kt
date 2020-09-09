package ru.storytellers.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameEndViewModel : BaseViewModel<DataModel>() {
    private val textOfStoryTallerLiveData = MutableLiveData<String>()
    private val uriBackgroundImageLiveData = MutableLiveData<Uri>()
    private val app = StoryTallerApp.instance
    private val gameStorage = app.gameStorage

    fun subscribeOnTextOfStoryTaller() = textOfStoryTallerLiveData
    fun subscribeOnUriBackgroundImage() = uriBackgroundImageLiveData

    fun setTextOfStoryTaller() {
        textOfStoryTallerLiveData.value = gameStorage
            .getSentences()
            .getSortedList()
            .toListOfStrings()
            .collectSentence()
    }

    fun getUriBackgroundImage() {
        gameStorage.getLocationGame()?.imageUrl?.let {
                uriBackgroundImageLiveData.value = resourceToUri(it)
            }
    }

    fun buttonSelectCoverClickedStat(){
        buttonClickedStatistic(StatHelper.buttonSelectCoverClicked)
    }
    fun buttonContinueClickedStat(){
        buttonClickedStatistic(StatHelper.buttonContinueClicked)
    }
    fun buttonCopyClickedStat(){
        buttonClickedStatistic(StatHelper.buttonCopyClicked)
    }

    private fun buttonClickedStatistic(nameButton:String){
        val clicked="clicked"
        val prop = listOf(
            nameButton to clicked,
            StatHelper.buttonClickedTime to getCurrentDateTime().getString()
        )
        app.stat.riseEvent(StatHelper.onGameEndScreen, prop.toProperties())
    }

}