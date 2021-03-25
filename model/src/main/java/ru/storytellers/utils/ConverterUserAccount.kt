package ru.storytellers.utils

import android.net.Uri
import ru.storytellers.model.datasource.remote.api.UserAccountApi
import ru.storytellers.model.entity.UserAccount

fun convertAccountToAccountApi(userAccount: UserAccount) =
    userAccount.run {
        UserAccountApi(
            nameUser = name,
            emailUser = email,
            idUser = id,
            idToken = idToken,
            photoUrlUser = photoUrl.toString()
        )
    }

fun convertAccountApiToAccount(userAccountApi: UserAccountApi) =
    userAccountApi.run {
        UserAccount(
            name = nameUser,
            email = emailUser,
            id = idUser,
            idToken = idToken,
            photoUrl = photoUrlUser  as Uri
        )
    }
