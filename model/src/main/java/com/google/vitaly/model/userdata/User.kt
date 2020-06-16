package com.google.vitaly.model.userdata

data class User(
    val userId: Int?,
    val userName: String?,
    val userAvatarUrl: String?,
    val userStory: List<Story> = listOf()
)
