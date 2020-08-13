package ru.storytellers.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameStartViewModel: BaseViewModel<DataModel>() {
    private val levelGameLiveData = MutableLiveData<Int>()

    fun getLevelGame(){
        levelGameLiveData.value=StoryTallerApp.instance.gameStorage.getLevelGame()
    }

    fun subscribeOnLevelGame()=levelGameLiveData
}