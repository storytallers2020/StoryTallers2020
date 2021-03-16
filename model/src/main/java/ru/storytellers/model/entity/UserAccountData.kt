package ru.storytellers.model.entity

import android.net.Uri

data class UserAccountData(
        val nameUser : String,
        val emailUser : String,
        val idUser : String,
        val idToken : String,
        val photoUrlUser : Uri?
)
