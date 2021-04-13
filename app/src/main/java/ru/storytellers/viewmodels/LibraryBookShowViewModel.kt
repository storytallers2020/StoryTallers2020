package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.itemClickedStat
import ru.storytellers.utils.collectSentence
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber

class LibraryBookShowViewModel(
    private val storyRepository: IStoryRepository
) : BaseViewModel<DataModel>() {
    private val textStoryLiveData = MutableLiveData<String>()
    private val sentencesLiveData = MutableLiveData<List<SentenceOfTale>>()
    private val titleStoryLiveData = MutableLiveData<String>()
    private val locationImageLiveData = MutableLiveData<String>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()
    private val onRemoveStoryLiveData = MutableLiveData<Int>()

    fun subscribeOnTextStory(): LiveData<String> {
        return textStoryLiveData
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

    fun subscribeOnLocationImage(): LiveData<String> {
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
                    locationImageLiveData.value = location.imageUrl
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

    fun removeStory(story: Story) {
        storyRepository.deleteStoryById(story.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ numberDeletedRecords ->
                onRemoveStoryLiveData.value = numberDeletedRecords
            }, { error ->
                Timber.e(error, "Remove story throwable")
            })
    }

    fun itemCopyClickedStat() {
        itemClickedStat(StatHelper.libraryBookScreenMenuItemCopyClicked)
    }

    fun itemEditClickedStat() {
        itemClickedStat(StatHelper.libraryBookScreenMenuItemEditClicked)
    }

    fun itemDeleteClickedStat() {
        itemClickedStat(StatHelper.libraryBookScreenMenuItemDeleteClicked)
    }

    fun itemShareClickedStat() {
        itemClickedStat(StatHelper.libraryBookScreenMenuItemShareClicked)
    }
}
