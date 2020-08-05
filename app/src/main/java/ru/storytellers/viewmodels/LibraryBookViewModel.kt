package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import java.lang.StringBuilder

class LibraryBookViewModel(
    private val storyRepository: IStoryRepository
): BaseViewModel<DataModel>() {
    private val textStoryLiveData = MutableLiveData<String>()
    private val titleStoryLiveData = MutableLiveData<String>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()

    fun subscribeOnTextStory(): LiveData<String>{
        return textStoryLiveData
    }
    fun subscribeOnTitleStory(): LiveData<String>{
        return titleStoryLiveData
    }
    fun subscribeOnError(): LiveData<DataModel.Error>{
        return onErrorliveData
    }

     fun getTextStory(story: Story){
         storyRepository.getStoryWithSentencesById(story.id)
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe({
                 val textStory = extractTextFromStory(it)
                 textStoryLiveData.value= textStory
             },{
                 onErrorliveData.value=DataModel.Error(it)
             })

    }

    private fun extractTextFromStory(story: Story): String{
        val textStory = StringBuilder()
        story.sentences?.forEach {
            textStory.append(it.content)
        }
        return textStory.toString()
    }

    fun getTitleStory(story: Story){
        titleStoryLiveData.value=story.name
    }
}