package ru.storytellers.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.utils.resourceToUri
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameEndViewModel: BaseViewModel<DataModel>() {
    private val textOfStoryTallerLiveData = MutableLiveData<String>()
    private val uriBackgroundImageLiveData = MutableLiveData<Uri>()


    fun subscribeOnTextOfStoryTaller()=textOfStoryTallerLiveData
    fun subscribeOnUriBackgroundImage()=uriBackgroundImageLiveData
    fun setTextOfStoryTaller(textOfStoryTaller:String){
        textOfStoryTallerLiveData.value=textOfStoryTaller
    }
    fun getUriBackgroundImage(){
        StoryTallerApp
            .instance
            .gameStorage
            .getLocationGame()?.
            imageUrl?.
            let { uriBackgroundImageLiveData.value=resourceToUri(it) }
    }

}