package ru.storytellers.model.userdata

data class UserOld(
    val id: Int?,
    val name: String?,
    val avatarUrl: String?,
    val stories: List<Story> = listOf()
)
