package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.utils.collectSentence
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import timber.log.Timber

class LibraryBookViewModel(
    private val storyRepository: IStoryRepository
) : BaseViewModel<DataModel>() {
    private val textStoryLiveData = MutableLiveData<String>()
    private val titleStoryLiveData = MutableLiveData<String>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()
    private val onRemoveStoryLiveData = MutableLiveData<Int>()

    fun subscribeOnTextStory(): LiveData<String> {
        return textStoryLiveData
    }

    fun subscribeOnRemoveStory(): LiveData<Int> {
        return onRemoveStoryLiveData
    }

    fun subscribeOnTitleStory(): LiveData<String> {
        return titleStoryLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun getTextStory(storyId: Long) {
        storyRepository.getStoryWithSentencesById(storyId)
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

    fun getTitleStory(title: String) {
        titleStoryLiveData.value = title
    }

    fun getTitleStory(story: Story) {
        titleStoryLiveData.value = story.name
    }

    fun removeStory(story: Story) {
        storyRepository.delete(story).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onRemoveStoryLiveData.value = it
            }, {
                Timber.e(it, "Remove story throwable")
            })
    }
}
