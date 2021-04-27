package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose

class VersionApi(
    @Expose val id: Int,
    @Expose val characterVersion: Int,
    @Expose val locationVersion: Int,
    @Expose val coverVersion: Int,
    @Expose val rusWordVersion: Int
)