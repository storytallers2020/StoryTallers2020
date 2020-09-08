package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.ILocationRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.utils.toProperties
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class LocationViewModel(private val locationRepository: ILocationRepository) :
    BaseViewModel<DataModel>() {
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

    fun setLocationGame(location: Location){
        StoryTallerApp.instance.gameStorage.setLocationGame(location)
    }

    fun onLocationChoiceStatistic(location: Location) {
        val prop = listOf(
            StatHelper.locationName to location.name,
            StatHelper.locationId to location.id.toString(),
            StatHelper.timeLocationChoice to getCurrentDateTime().getString()
        )
        stat.riseEvent(StatHelper.onLocationChoice, prop.toProperties())
    }

}