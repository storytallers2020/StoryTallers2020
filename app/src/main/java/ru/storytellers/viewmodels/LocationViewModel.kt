package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Location
import ru.storytellers.model.repository.ILocationRepository

class LocationViewModel(private val locationRepository: ILocationRepository) :
    BaseViewModel<DataModel>() {
    //Неиспользуемая переменная
    //private val locationsLiveData = MutableLiveData<List<Location>>()
    // camelCase onSuccessliveData -> onSuccessLiveData
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Location>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    fun getAllLocations() {
        locationRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Location>> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

}