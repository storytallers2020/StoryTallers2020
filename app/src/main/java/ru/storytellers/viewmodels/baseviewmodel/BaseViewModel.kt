package ru.storytellers.viewmodels.baseviewmodel

import androidx.lifecycle.ViewModel
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.model.DataModel
import ru.storytellers.utils.StatHelper

abstract class BaseViewModel<T : DataModel>(
): ViewModel() {
    fun onBackClicked(fragmentName: String) {
        StoryHeroesApp.instance
            .stat.riseEvent("$fragmentName : ${StatHelper.buttonBackClicked}")
    }
}