package ru.storytellers.viewmodels.baseviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.storytellers.model.DataModel

abstract class BaseViewModel<T : DataModel>(
): ViewModel() {
}