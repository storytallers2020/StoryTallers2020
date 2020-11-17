package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.repository.ISentenceOfTaleRepository
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class EditingFairyTaleViewModel(
    private val storyRepository: IStoryRepository,
    private val sentenceOfTaleRepository: ISentenceOfTaleRepository
): BaseViewModel<DataModel>() {

    private val updateTitleStoryLiveData = MutableLiveData<Int>()
    private val onErrorUpdateTitleStoryLiveData = MutableLiveData<Throwable>()
    private val editSentenceLiveData = MutableLiveData<Boolean>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    fun subscribeOnUpdateTitleStory(): LiveData<Int> {
        return updateTitleStoryLiveData
    }
    fun subscribeOnEditSentence(): LiveData<Boolean> {
        return editSentenceLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun editSentence(storyId: Long, sourceSentence: SentenceOfTale, newText: String) {
        if (sourceSentence.content != newText && newText.isNotEmpty()) {
            sourceSentence.content = newText
            sentenceOfTaleRepository.insertOrReplace(storyId, sourceSentence)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    editSentenceLiveData.value = true
                }, {
                    editSentenceLiveData.value = false
                })
        }
    }


    fun updateTitleStory(titleStory: String, storyId: Long) {
        storyRepository.updateTitleStory(titleStory, storyId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ numberUpdatedRecords ->
                updateTitleStoryLiveData.value = numberUpdatedRecords
            }, {error ->
                onErrorUpdateTitleStoryLiveData.value = error
            })
    }
}