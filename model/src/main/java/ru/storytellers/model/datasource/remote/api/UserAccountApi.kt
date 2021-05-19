package ru.storytellers.model.datasource.remote.api


import com.google.gson.annotations.Expose

data class UserAccountApi(
    @Expose val name: String,
    @Expose val userId: String,
    @Expose val avatarUrl: String
)
