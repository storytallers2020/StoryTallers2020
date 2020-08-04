package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.engine.GameStorage
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import java.lang.StringBuilder

class LibraryBookViewModel: BaseViewModel<DataModel>() {
    private val textStoryLiveData = MutableLiveData<String>()
    private val titleStoryLiveData = MutableLiveData<String>()

    fun subscribeOnTextStory(): LiveData<String>{
        return textStoryLiveData
    }
    fun subscribeOnTitleStory(): LiveData<String>{
        return titleStoryLiveData
    }

     fun getTextStory(story: Story){
        val textStory= StringBuilder()

        story.sentences?.forEach {
            textStory.append(it.content)
        }
        textStoryLiveData.value= textStory.toString()
    }

    fun getTitleStory(story: Story){
        titleStoryLiveData.value=story.name
    }
}