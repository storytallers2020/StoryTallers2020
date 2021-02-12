package ru.storytellers.viewmodels.baseviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.utils.StatHelper

abstract class BaseViewModel<T : DataModel>(
) : ViewModel() {
    private val loadingStateLiveData = MutableLiveData<Boolean>()
    fun onBackClicked(fragmentName: String) {
        StoryHeroesApp.instance
            .stat.riseEvent("$fragmentName : ${StatHelper.buttonBackClicked}")
    }

    protected fun setLoadingStateLiveData(isLoading: Boolean) {
        loadingStateLiveData.value = isLoading
    }

    fun subscribeOnProgressEnableLiveData(): LiveData<Boolean> {
        return loadingStateLiveData
    }
}