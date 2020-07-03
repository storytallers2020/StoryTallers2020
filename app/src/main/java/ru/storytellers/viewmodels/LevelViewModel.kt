package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel
import kotlin.properties.Delegates

class LevelViewModel : BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve: LiveData<DataModel> = _mutableLiveData
    private var levelGame:Int=0


    override fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }
    fun setLevelGame(levelGame:Int){
        this.levelGame=levelGame
    }
}


