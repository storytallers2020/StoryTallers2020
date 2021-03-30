package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose
import ru.storytellers.model.entity.Character

data class WordListApi (
    @Expose val words : List<String>
)