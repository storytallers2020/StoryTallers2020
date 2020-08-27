package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.utils.collectSentence
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber

class LibraryViewModel(
    private val storyRepository: IStoryRepository
) : BaseViewModel<DataModel>() {
    private val onSuccessliveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()
    private val onLoadingliveData = MutableLiveData<DataModel.Loading>()
    private val onRemoveStoryLiveData = MutableLiveData<Int>()
    private val textStoryLiveData = MutableLiveData<String>()
    private val titleStoryLiveData = MutableLiveData<String>()
    private val gameStorage = StoryTallerApp.instance.gameStorage

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Story>> {
        return onSuccessliveData
    }

    fun subscribeRnRemoveStory(): LiveData<Int> {
        return onRemoveStoryLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorliveData
    }

    fun subscribeOnLoading(): LiveData<DataModel.Loading> {
        return onLoadingliveData
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
                onSuccessliveData.value = DataModel.Success(it)
            }, {
                onErrorliveData.value = DataModel.Error(it)
            })
    }

    fun removeStory(story: Story) {
        storyRepository.delete(story).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onRemoveStoryLiveData.value = it
            }, {
                Timber.e(it, "Remove story throwable")
            })
    }


    fun getTextStory(story: Story) {
        storyRepository.getStoryWithSentencesById(story.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //val textStory = extractTextFromStory(it)
                it.sentences?.let { sentences ->
                    textStoryLiveData.value = mapToList(sentences).collectSentence()
                }
            }, {
                onErrorliveData.value = DataModel.Error(it)
            })

    }

    private fun mapToList(sentences: List<SentenceOfTale>): List<String> =
        sentences.map { it.content }


    fun getTitleStory(story: Story) {
        titleStoryLiveData.value = story.name
    }

    fun onClearStorage() {
        gameStorage.apply {
            clearTitleStory()
            clearCoverStoryTaller()
            clearLocationGame()
            clearListPlayers()
            clearListSentenceOfTale()
            setLevelGame(0)
        }
    }
}