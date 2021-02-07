package ru.storytellers.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player (
    val id: Long,
    val name: String,
    val character: Character?
): Parcelable