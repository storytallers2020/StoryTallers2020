package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose
import ru.storytellers.model.entity.Cover


data class CoverListApi (
    @Expose val covers : List<Cover>
)