package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.utils.toProperties

class StartViewModel(
    private val storyRepository: IStoryRepository
) : BaseViewModel<DataModel>() {
    private val stat = StoryTallerApp.instance.stat
    private val onSuccessliveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()
    private val onLoadingliveData = MutableLiveData<DataModel.Loading>()

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Story>> {
        return onSuccessliveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorliveData
    }
    fun subscribeOnLoading() : LiveData<DataModel.Loading> {
        return onLoadingliveData
    }
    fun toRulesScreenStatistics(){
        val prop = listOf(
            StatHelper.userId to "userId", // заглушка пока что
            StatHelper.timeOnRulesGame to getCurrentDateTime().getString()
        )
        stat.riseEvent(StatHelper.onRulesGame, prop.toProperties())
    }
    fun createTaleStatistics(){
        val prop = listOf(
            StatHelper.userId to "userId", // заглушка пока что
            StatHelper.timeStartCreateTale to getCurrentDateTime().getString()
        )
        stat.riseEvent(StatHelper.startCreateTale, prop.toProperties())
    }
    fun onLibraryScreenStatistics(){
        val prop = listOf(
            StatHelper.timeToLibraryScreen to getCurrentDateTime().getString()
        )
        stat.riseEvent(StatHelper.onLibraryScreen,prop.toProperties())
    }
    fun onAboutScreenStatistics(){
        stat.riseEvent(StatHelper.onAboutScreen)
    }

    fun getAllStory(){
        storyRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessliveData.value=DataModel.Success(it)
            },{
                onErrorliveData.value=DataModel.Error(it)
            })
    }

}