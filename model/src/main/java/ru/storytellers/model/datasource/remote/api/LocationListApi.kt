package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose
import ru.storytellers.model.entity.Location


data class LocationListApi (
    @Expose val locations : List<Location>
)