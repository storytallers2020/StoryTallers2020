package ru.storytellers.utils

import ru.storytellers.model.datasource.remote.api.UserAccountApi
import ru.storytellers.model.entity.UserAccount

fun convertAccountToAccountApi(userAccount: UserAccount) =
    userAccount.run {
        UserAccountApi(
            name = name,
            idToken = idToken,
            avatarUrl = photoUrl.toString()
        )
    }


