package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.repository.remote.IRemoteRepository


class SplashViewModel(
    private val remoteRepository: IRemoteRepository
) : ViewModel() {
    private val onSuccessLiveData = MutableLiveData<Boolean>()
    private val onErrorLiveData = MutableLiveData<Throwable>()
    private val onLoadingLiveData = MutableLiveData<Boolean>()

    fun subscribeOnSuccess(): LiveData<Boolean> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<Throwable> {
        return onErrorLiveData
    }

    fun subscribeOnLoading(): LiveData<Boolean> {
        return onLoadingLiveData
    }

    fun loadResource() {
        onLoadingLiveData.value = true
        remoteRepository.cacheCharacters()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = true
                onLoadingLiveData.value = false
            }, {
                onErrorLiveData.value = it
                onLoadingLiveData.value = false
            })
    }
}