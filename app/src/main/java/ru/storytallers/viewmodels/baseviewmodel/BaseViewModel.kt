package ru.storytallers.viewmodels.baseviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.storytellers.model.DataModel

abstract class BaseViewModel<T : DataModel>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()

): ViewModel() {
    abstract fun subscribe(): LiveData<DataModel>
}