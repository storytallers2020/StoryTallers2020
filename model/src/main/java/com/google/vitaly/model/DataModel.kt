package com.google.vitaly.model.data

import com.google.vitaly.model.data.userdata.Result

sealed class DataModel {

    data class Success(val data: List<Result>?) : DataModel()
    data class Error(val error: Throwable) : DataModel()
    data class Loading(val progress: Int?) : DataModel()
}
