package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose

data class UserApi(
    @Expose val name: String,
    @Expose val userId: Long,
    @Expose val avatarUrl: String

)
