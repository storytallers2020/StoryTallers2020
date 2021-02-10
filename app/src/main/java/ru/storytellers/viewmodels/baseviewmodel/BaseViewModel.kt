package ru.storytellers.viewmodels.baseviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.utils.StatHelper

abstract class BaseViewModel<T : DataModel>(
): ViewModel() {
    protected  val onProgressEnableLiveData = MutableLiveData<Boolean>()
    fun onBackClicked(fragmentName: String) {
        StoryHeroesApp.instance
            .stat.riseEvent("$fragmentName : ${StatHelper.buttonBackClicked}")
    }

    protected fun setFalseInProgressEnableLiveData(){
        onProgressEnableLiveData.value=false
    }

    protected fun setTrueInProgressEnableLiveData(){
        onProgressEnableLiveData.value=true
    }

     fun subscribeOnProgressEnableLiveData(): LiveData<Boolean> {
        return onProgressEnableLiveData
    }
}