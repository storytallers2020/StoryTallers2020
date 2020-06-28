package ru.storytallers.viewmodels


import androidx.lifecycle.LiveData
import ru.storytallers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel

class CreateCharacterViewModel : BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve: LiveData<DataModel> = _mutableLiveData
    override fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }
}


