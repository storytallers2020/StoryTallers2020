package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose


class VersionsApiList (
    @Expose val versions: List<VersionApi>
)