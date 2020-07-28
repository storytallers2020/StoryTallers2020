package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Cover
import ru.storytellers.ui.assistant.TitleAndSaveModelAssistant
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class TitleAndSaveStoryViewModel(
    private val assistantModel:TitleAndSaveModelAssistant
): BaseViewModel<DataModel>() {
    private var titleStory:String?=null
    private val coverLiveDate= MutableLiveData<Cover>()

    fun setTitleStory(title:String){
        titleStory= title
        StoryTallerApp.instance.gameStorage.setTitleStory(titleStory!!)
        val asd="sdf"
    }

    fun subscribeOnCover():LiveData<Cover>{
        coverLiveDate.value=StoryTallerApp.instance.gameStorage.getCoverStoryTaller()
        return coverLiveDate
    }
}