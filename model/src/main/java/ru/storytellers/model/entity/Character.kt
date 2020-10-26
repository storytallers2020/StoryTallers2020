package ru.storytellers.model.entity

import com.google.gson.annotations.Expose

data class Character(
    @Expose val id: Long,
    @Expose val name: String,
    @Expose val avatarUrl: String,
    @Expose val avatarUrlSelected: String
)