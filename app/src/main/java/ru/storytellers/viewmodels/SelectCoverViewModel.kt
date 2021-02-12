package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.repository.ICoverRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class SelectCoverViewModel(
    private val coverRepository: ICoverRepository
) : BaseViewModel<DataModel>() {
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Cover>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    fun getAllCover() {
        setLoadingStateLiveData(true)
        coverRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
                setLoadingStateLiveData(false)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
                setLoadingStateLiveData(false)
            })
    }

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Cover>> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun setCoverStory(cover: Cover) {
        StoryHeroesApp.instance.gameStorage.setCoverStory(cover)
    }

    fun coverStatistics(cover: Cover) {
        val prop = listOf(
            StatHelper.coverName to cover.name,
            StatHelper.coverId to cover.id.toString(),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.selectCoverScreenCoverSelected, prop)
    }

}