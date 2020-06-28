package ru.storytallers.viewmodels


import androidx.lifecycle.LiveData
import ru.storytallers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytallers.model.DataModel

class LocationViewModel : BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve: LiveData<DataModel> = _mutableLiveData
    public override fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }
}


