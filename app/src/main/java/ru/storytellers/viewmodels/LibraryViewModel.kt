package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class LibraryViewModel(
    private val storyRepository: IStoryRepository
): BaseViewModel<DataModel>() {
    private val onSuccessliveData = MutableLiveData<DataModel.Success<Story>>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()
    private val onLoadingliveData = MutableLiveData<DataModel.Loading>()
    private val gameStorage= StoryTallerApp.instance.gameStorage

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Story>> {
        return onSuccessliveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorliveData
    }
    fun subscribeOnLoading() : LiveData<DataModel.Loading> {
        return onLoadingliveData
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

    fun removeStoryTalle(storyId:Long){
        //
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