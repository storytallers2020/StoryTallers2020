package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel


class LevelViewModel : BaseViewModel<DataModel>() {
    private val levelGameLiveData= MutableLiveData<Int>()
    private var levelGame:Int=0



    fun subscribeOnLevelGame():LiveData<Int>{
        return levelGameLiveData
    }

    fun setLevelGame(levelGame:Int){
        this.levelGame=levelGame
        levelGameLiveData.value=this.levelGame
    }
}


