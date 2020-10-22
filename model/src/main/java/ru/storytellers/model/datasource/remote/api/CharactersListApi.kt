package ru.storytellers.model.datasource.remote.api

import com.google.gson.annotations.Expose
import ru.storytellers.model.entity.Character

data class CharactersListApi (
    @Expose val characters : List<Character>
)