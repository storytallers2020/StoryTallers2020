package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryHeroesApp
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
    private val onChangedListLiveData = MutableLiveData<List<Story>>()
    private val storyLiveData = MutableLiveData<Story>()
    private val textStoryLiveData = MutableLiveData<String>()
    private val titleStoryLiveData = MutableLiveData<String>()

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Story>> {
        return onSuccessLiveData
    }

    fun subscribeOnLoading(): LiveData<DataModel.Loading> {
        return onLoadingLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun subscribeOnTitleStory(): LiveData<String> {
        return titleStoryLiveData
    }

    fun subscribeOnTextStory(): LiveData<String> {
        return textStoryLiveData
    }

    fun subscribeOnDeleteStory(): LiveData<Int> {
        return onRemoveStoryLiveData
    }

    fun subscribeOnChangedList(): LiveData<List<Story>> {
        return onChangedListLiveData
    }

    fun getAllStory() {
        onLoadingLiveData.value = DataModel.Loading(1)
        storyRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
                onLoadingLiveData.value = DataModel.Loading(100)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
                onLoadingLiveData.value = DataModel.Loading(-1)
            })
    }

    fun setStoryLiveData(story: Story) {
        storyLiveData.value = story
        setTitleStory()
        setTextStory()
    }

    private fun setTitleStory() {
        titleStoryLiveData.value = storyLiveData.value?.name
    }

    private fun setTextStory() {
        storyLiveData.value?.id?.let { id ->
            storyRepository.getStoryWithSentencesById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ story ->
                    story.sentences?.let { sentences ->
                        textStoryLiveData.value = mapToList(sentences).collectSentence()
                    }
                }, {
                    onErrorLiveData.value = DataModel.Error(it)
                })
        }
    }

    private fun mapToList(sentences: List<SentenceOfTale>): List<String> =
        sentences.map { it.content }

    fun deleteStory() {
        storyLiveData.value?.let { story ->
            storyRepository.deleteStoryById(story.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ storiesRemoved ->
                    onRemoveStoryLiveData.value = storiesRemoved
                    onSuccessLiveData.value?.data?.apply {
                        onChangedListLiveData.value = toMutableList().apply {
                            remove(story)
                        }
                    }
                }, {
                    Timber.e(it, "Remove story throwable")
                })
        }
    }

    fun onClearStorage() {
        StoryHeroesApp.instance.gameStorage.clear()
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
        StoryHeroesApp.instance.stat.riseEvent(StatHelper.libraryScreenBtnStartScreenClicked)
    }
}