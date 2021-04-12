package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose

data class WordListApi (
    @Expose val words : List<String>
)