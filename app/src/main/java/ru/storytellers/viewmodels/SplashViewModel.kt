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

    fun subscribeOnSuccess(): LiveData<Boolean> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<Throwable> {
        return onErrorLiveData
    }

    fun subscribeOnLoading(): LiveData<Int> {
        return onLoadingLiveData
    }

    fun loadResource() {
        onLoadingLiveData.value = 0
        versionRepository.getVersions()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onLoadingLiveData.value = 10
                versionProcessing(it)
            }, {
                onErrorLiveData.value = it
            })

    }

    private fun versionProcessing(versions: VersionsComparator) {
        if (!versions.isCharacterActual) cacheCharacters(versions)
    }

    private fun cacheCharacters(versions: VersionsComparator) {
        remoteRepository.cacheCharacters()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onLoadingLiveData.value = 30
                updateLocalVersion(versions)
            }, {
                onErrorLiveData.value = it
            })
    }

    private fun updateLocalVersion(versions: VersionsComparator) {
        versions.localVersions.characterVersion = versions.remoteVersions.characterVersion
        versionRepository.insertOrReplace(versions.localVersions)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            onLoadingLiveData.value = 40
        }, {
            onErrorLiveData.value = it
        })
    }
}