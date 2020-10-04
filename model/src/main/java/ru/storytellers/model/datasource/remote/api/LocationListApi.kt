package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose


data class LocationListApi (
    @Expose val locations : List<LocationApi>
)