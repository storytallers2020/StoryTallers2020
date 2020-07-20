package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.ILocationRepository

class LocationViewModel(private val locationRepository: ILocationRepository) : BaseViewModel<DataModel>() {
    private val locationsLiveData = MutableLiveData<List<Location>>()
    private val onSuccessliveData = MutableLiveData<DataModel.Success<Location>>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()

    fun getAllLocations(){
        locationRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessliveData.value=DataModel.Success(it)
            },{
                onErrorliveData.value=DataModel.Error(it)
            })
    }

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Location>> {
        return onSuccessliveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorliveData
    }

}


