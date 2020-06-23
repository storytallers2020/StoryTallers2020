package com.google.vitaly.model.userdata

data class User(
    val id: Int?,
    val name: String?,
    val avatarUrl: String?,
    val stories: List<Story> = listOf()
)
