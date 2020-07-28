package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.Location
import ru.storytellers.model.repository.ICoverRepository
import ru.storytellers.model.repository.ILocationRepository
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class SelectCoverViewModel(
    private val coverRepository: ICoverRepository
): BaseViewModel<DataModel>() {
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Cover>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    fun getAllCover() {
        coverRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Cover>> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }
}