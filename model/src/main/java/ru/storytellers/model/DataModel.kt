package ru.storytellers.model



sealed class DataModel {
    data class Success<T>(val data: List<T>?) : DataModel()
    data class Error(val error: Throwable) : DataModel()
    data class Loading(val progress: Int?) : DataModel()
}
