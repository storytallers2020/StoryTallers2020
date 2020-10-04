package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose

data class LocationApi(
    @Expose val id: String,
    @Expose val name: String,
    @Expose val imageUrl: String,
    @Expose val imageForRecycler: String,
    @Expose val descriptions: String
)