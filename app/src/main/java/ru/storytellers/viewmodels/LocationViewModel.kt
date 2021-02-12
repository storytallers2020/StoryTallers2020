package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Location
import ru.storytellers.model.repository.ILocationRepository
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class LocationViewModel(private val locationRepository: ILocationRepository) :
    BaseViewModel<DataModel>() {
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Location>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    fun getAllLocations() {
        setLoadingStateLiveData(true)
        locationRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
                setLoadingStateLiveData(false)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
                setLoadingStateLiveData(false)
            })
    }

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Location>> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun setLocationGame(location: Location) {
        StoryHeroesApp.instance.gameStorage.setLocationGame(location)
    }

    fun onLocationChoiceStatistic(location: Location) {
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString(),
            StatHelper.locationName to location.name,
            StatHelper.locationId to location.id.toString()
        )
        riseEvent(StatHelper.locationScreenLocationSelected, prop)
    }

}