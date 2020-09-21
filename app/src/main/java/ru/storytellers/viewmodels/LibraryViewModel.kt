package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.itemClickedStat
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.collectSentence
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber

class LibraryViewModel(
    private val storyRepository: IStoryRepository
) : BaseViewModel<DataModel>() {
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()
    private val onLoadingLiveData = MutableLiveData<DataModel.Loading>()

    private val onRemoveStoryLiveData = MutableLiveData<Int>()
    private val textStoryLiveData = MutableLiveData<String>()
    private val titleStoryLiveData = MutableLiveData<String>()

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Story>> {
        return onSuccessLiveData
    }

    fun subscribeOnDeleteStory(): LiveData<Int> {
        return onRemoveStoryLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun subscribeOnLoading(): LiveData<DataModel.Loading> {
        return onLoadingLiveData
    }

    fun subscribeOnTextStory(): LiveData<String> {
        return textStoryLiveData
    }

    fun subscribeOnTitleStory(): LiveData<String> {
        return titleStoryLiveData
    }


    fun getAllStory() {
        storyRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

    fun deleteStory(story: Story) {
        storyRepository.deleteStoryById(story.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ storiesRemoved ->
                onRemoveStoryLiveData.value = storiesRemoved
            }, {
                Timber.e(it, "Remove story throwable")
            })
    }

    fun getTextStory(story: Story) {
        storyRepository.getStoryWithSentencesById(story.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.sentences?.let { sentences ->
                    textStoryLiveData.value = mapToList(sentences).collectSentence()
                }
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

    private fun mapToList(sentences: List<SentenceOfTale>): List<String> =
        sentences.map { it.content }


    fun getTitleStory(story: Story) {
        titleStoryLiveData.value = story.name
    }

    fun onClearStorage() {
        StoryTallerApp.instance.gameStorage.clear()
    }


    fun storySelectedStat(story: Story) {
        val prop = listOf(
            StatHelper.storyName to story.name,
            StatHelper.storyId to story.id.toString(),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.libraryScreenStorySelected, prop)
    }

    fun itemCopyClickedStat() {
        itemClickedStat(StatHelper.libraryScreenMenuItemCopyClicked)
    }

    fun itemDeleteClickedStat() {
        itemClickedStat(StatHelper.libraryScreenMenuItemDeleteClicked)
    }

    fun itemShareClickedStat() {
        itemClickedStat(StatHelper.libraryScreenMenuItemShareClicked)
    }

    fun btnToStartScreenClickedStat() {
        StoryTallerApp.instance.stat.riseEvent(StatHelper.libraryScreenBtnStartScreenClicked)
    }
}