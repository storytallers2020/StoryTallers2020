package ru.storytellers.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.storytellers.model.DataModel
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameEndViewModel: BaseViewModel<DataModel>() {
    private val textOfStoryTallerLiveData = MutableLiveData<String>()

    fun subscribeOnTextOfStoryTaller()=textOfStoryTallerLiveData
    fun setTextOfStoryTaller(textOfStoryTaller:String){
        textOfStoryTallerLiveData.value=textOfStoryTaller
    }
}