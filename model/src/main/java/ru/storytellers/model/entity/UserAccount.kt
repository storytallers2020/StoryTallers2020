package ru.storytellers.model.entity

import android.net.Uri

data class UserAccount(
        val name: String,
        val email: String,
        val id: String,
        val idToken: String,
        val photoUrl: Uri?
)
