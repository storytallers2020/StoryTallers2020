package ru.storytellers.model.entity

data class Character(
    val id: Long,
    val name: String,
    val avatarUrl: String,
    val avatarUrlSelected: String
)