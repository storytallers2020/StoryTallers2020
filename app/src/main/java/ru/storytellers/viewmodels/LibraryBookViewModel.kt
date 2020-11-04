package ru.storytellers.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.ISentenceOfTaleRepository
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.itemClickedStat
import ru.storytellers.utils.collectSentence
import ru.storytellers.utils.resourceToUri
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber

class LibraryBookViewModel(
    private val storyRepository: IStoryRepository,
    private val sentenceOfTaleRepository: ISentenceOfTaleRepository
) : BaseViewModel<DataModel>() {
    private val textStoryLiveData = MutableLiveData<String>()
    private val sentencesLiveData = MutableLiveData<List<SentenceOfTale>>()
    private val titleStoryLiveData = MutableLiveData<String>()
    private val locationImageLiveData = MutableLiveData<Uri>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()
    private val onRemoveStoryLiveData = MutableLiveData<Int>()
    private val editSentenceLiveData = MutableLiveData<Boolean>()

    fun subscribeOnTextStory(): LiveData<String> {
        return textStoryLiveData
    }
    fun subscribeOnEditSentence(): LiveData<Boolean> {
        return editSentenceLiveData
    }

    fun subscribeOnSentences(): LiveData<List<SentenceOfTale>> {
        return sentencesLiveData
    }

    fun subscribeOnRemoveStory(): LiveData<Int> {
        return onRemoveStoryLiveData
    }

    fun subscribeOnTitleStory(): LiveData<String> {
        return titleStoryLiveData
    }

    fun subscribeOnLocationImage(): LiveData<Uri> {
        return locationImageLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun getTextStory(storyId: Long) {
        storyRepository.getStoryWithSentencesById(storyId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ story ->
                story.sentences?.let { sentences ->
                    sentencesLiveData.value = sentences
                    textStoryLiveData.value = mapToList(sentences).collectSentence()
                }
                story.location?.let { location ->
                    locationImageLiveData.value = resourceToUri(location.imageUrl)
                }
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })

    }

    private fun mapToList(sentences: List<SentenceOfTale>): List<String> =
        sentences.map { it.content }

    fun getTitleStory(title: String) {
        titleStoryLiveData.value = title
    }

    fun getTitleStory(story: Story) {
        titleStoryLiveData.value = story.name
    }

    fun editSentence(storyId: Long, sourceSentence: SentenceOfTale, newText:String){
        if(sourceSentence.content != newText && newText.isNotEmpty()){
            sourceSentence.content=newText
            sentenceOfTaleRepository.insertOrReplace(storyId,sourceSentence)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    editSentenceLiveData.value = true
                }, {
                        editSentenceLiveData.value = false
                    })

        }

    }

    fun removeStory(story: Story) {
        storyRepository.deleteStoryById(story.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onRemoveStoryLiveData.value = it
            }, {
                Timber.e(it, "Remove story throwable")
            })
    }
    fun itemCopyClickedStat(){
        itemClickedStat(StatHelper.libraryBookScreenMenuItemCopyClicked)
    }
    fun itemDeleteClickedStat(){
        itemClickedStat(StatHelper.libraryBookScreenMenuItemDeleteClicked)
    }
    fun itemShareClickedStat(){
        itemClickedStat(StatHelper.libraryBookScreenMenuItemShareClicked)
    }
}
