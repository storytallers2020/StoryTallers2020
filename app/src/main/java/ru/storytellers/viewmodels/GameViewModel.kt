package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import ru.storytellers.model.DataModel
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameViewModel: BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve: LiveData<DataModel> = _mutableLiveData
    override fun subscribe(): LiveData<DataModel> = liveDataForViewToObserve
}