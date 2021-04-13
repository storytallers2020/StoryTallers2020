package ru.storytellers.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story (
    val id: Long,
    var name: String,
    var authors: String,
    var coverUrl: String,
    var location: Location?,
    var sentences: List<SentenceOfTale>?
): Parcelable