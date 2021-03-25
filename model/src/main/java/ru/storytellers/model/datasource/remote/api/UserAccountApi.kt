package ru.storytellers.model.datasource.remote.api


import com.google.gson.annotations.Expose

data class UserAccountApi(
    @Expose val nameUser: String,
    @Expose val emailUser: String,
    @Expose val idUser: String,
    @Expose val idToken: String,
    @Expose val photoUrlUser: String
)
