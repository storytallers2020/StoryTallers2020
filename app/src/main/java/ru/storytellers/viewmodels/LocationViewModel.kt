package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel

class LocationViewModel : BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve: LiveData<DataModel> = _mutableLiveData
    public override fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }
}


