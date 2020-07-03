package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel

class LevelViewModel : BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve: LiveData<DataModel> = _mutableLiveData

    override fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }
}


