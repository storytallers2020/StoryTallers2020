package ru.storytellers.model.userdata

import ru.storytellers.model.entity.Story

data class UserOld(
    val id: Int?,
    val name: String?,
    val avatarUrl: String?,
    val stories: List<Story> = listOf()
)
