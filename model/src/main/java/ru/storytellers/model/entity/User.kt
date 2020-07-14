package ru.storytellers.model.entity

data class User (
    val id: Long,
    val nickName: String,
    val login: String,
    val pass: String,
    val email: String,
    val avatarUrl: String,
    val currentToken: String
)

