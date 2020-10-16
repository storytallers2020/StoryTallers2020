package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel


class StartViewModel(
    private val storyRepository: IStoryRepository
) : BaseViewModel<DataModel>() {
    private val onSuccessliveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()
    private val onLoadingliveData = MutableLiveData<DataModel.Loading>()

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Story>> {
        return onSuccessliveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorliveData
    }

    fun subscribeOnLoading(): LiveData<DataModel.Loading> {
        return onLoadingliveData
    }

    fun toRulesScreenStatistics() {
        val prop = listOf(
            StatHelper.userId to "userId", // заглушка пока что
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.startScreenBtnToRulesGame, prop)
    }

    fun createTaleStatistics() {
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString(),
            StatHelper.userId to "userId" // заглушка пока что
        )
        riseEvent(StatHelper.startScreenBtnToCreateTale, prop)
    }

    fun onLibraryScreenStatistics() {
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.startScreenBtnToLibraryScreen, prop)
    }

    fun timeCreateStory(){
        StoryHeroesApp.instance.gameStorage.setTimeCreateStory(getCurrentDateTime().time)
    }

    fun onAboutScreenStatistics() {
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.startScreenBtnToAboutScreen, prop)
    }
    fun onStartScreenNumberOfTaleStat(numberOfTale:Int) {
        val prop = listOf(
            StatHelper.startScreenNumberOfTale to numberOfTale.toString()
        )
        riseEvent(StatHelper.startScreen, prop)
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

}