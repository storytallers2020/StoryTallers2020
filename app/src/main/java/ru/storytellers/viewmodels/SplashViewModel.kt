package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.model.entity.VersionsComparator
import ru.storytellers.model.repository.IVersionRepository
import ru.storytellers.model.repository.remote.IRemoteRepository


class SplashViewModel(
    private val versionRepository: IVersionRepository,
    private val remoteRepository: IRemoteRepository
) : ViewModel() {
    private val onSuccessLiveData = MutableLiveData<Boolean>()
    private val onErrorLiveData = MutableLiveData<Throwable>()
    private val onLoadingLiveData = MutableLiveData<Int>()
    private val onCloseApp = MutableLiveData<Boolean>()

    fun subscribeOnSuccess(): LiveData<Boolean> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<Throwable> {
        return onErrorLiveData
    }

    fun subscribeOnLoading(): LiveData<Int> {
        return onLoadingLiveData
    }

    fun subscribeOnCloseApp(): LiveData<Boolean> {
        return onCloseApp
    }

    fun loadResource() {
        onLoadingLiveData.value = 0
        versionRepository.getVersions()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onLoadingLiveData.value = 20
                versionProcessing(it)
            }, {
                onErrorLiveData.value = it
            })
    }

    private fun versionProcessing(versions: VersionsComparator) {
        if (!versions.isActual())
            cacheResources(versions)
        else {
            onLoadingLiveData.value = 100
            onSuccessLiveData.value = true
        }
    }

    //TODO: Сделать выбор языка обязательных слов приложения (после появления личного кабинета)
    private fun cacheResources(versions: VersionsComparator) {
        remoteRepository.cacheCharacters()
            .andThen(remoteRepository.cacheLocations())
            .andThen(remoteRepository.cacheCovers())
            .andThen(remoteRepository.cacheWords("rus"))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onLoadingLiveData.value = 50
                updateLocalVersion(versions)
            }, {
                onErrorLiveData.value = it
            })
    }

    private fun updateLocalVersion(versions: VersionsComparator) {
        versionRepository.insertOrReplace(versions.remoteVersions)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onLoadingLiveData.value = 100
                onSuccessLiveData.value = true
            }, {
                onErrorLiveData.value = it
            })
    }

    fun onUserSelectClose() {
        onCloseApp.value = true
    }

    fun onUserSelectContinue() {
        onSuccessLiveData.value = true
    }
}