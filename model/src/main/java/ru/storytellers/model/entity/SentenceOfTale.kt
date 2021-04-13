package ru.storytellers.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SentenceOfTale (
    val id: Long,
    val player: Player?,
    val step: Int,
    var content: String,
    val contentType: ContentTypeEnum
): Parcelable