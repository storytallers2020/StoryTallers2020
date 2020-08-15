package ru.storytellers.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.utils.collectSentence
import ru.storytellers.utils.getSortedList
import ru.storytellers.utils.resourceToUri
import ru.storytellers.utils.toListOfStrings
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameEndViewModel : BaseViewModel<DataModel>() {
    private val textOfStoryTallerLiveData = MutableLiveData<String>()
    private val uriBackgroundImageLiveData = MutableLiveData<Uri>()
    private val gameStorage = StoryTallerApp.instance.gameStorage

    fun subscribeOnTextOfStoryTaller() = textOfStoryTallerLiveData
    fun subscribeOnUriBackgroundImage() = uriBackgroundImageLiveData
//    fun setTextOfStoryTaller(textOfStoryTaller:String){
//        StoryTallerApp.instance.gameStorage.getListSentenceOfTale()
//    }

    fun setTextOfStoryTaller() {
        val sentences = gameStorage.getListSentenceOfTale()
        textOfStoryTallerLiveData.value = sentences
            .getSortedList()
            .toListOfStrings()
            .collectSentence()
    }

    fun getUriBackgroundImage() {
        gameStorage.getLocationGame()?.imageUrl?.let {
                uriBackgroundImageLiveData.value = resourceToUri(it)
            }
    }

}