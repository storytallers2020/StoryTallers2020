package ru.storytellers.utils

import ru.storytellers.model.datasource.remote.api.UserApi
import ru.storytellers.model.entity.User
import ru.storytellers.model.entity.UserAccount


fun convertUserToUserApi(user: User): UserApi =
    user.run {
        UserApi(
            name = name,
            userId = id,
            avatarUrl = avatarUrl
        )
    }

fun convertAccountToUser(userAccount: UserAccount): User =
    userAccount.run {
        User(
            id = email.hashCode().toLong(),
            name = name,
            email = email,
            avatarUrl = photoUrl.toString()
        )
    }




