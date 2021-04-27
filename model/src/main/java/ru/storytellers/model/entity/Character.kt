package ru.storytellers.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    @Expose val id: Long,
    @Expose val name: String,
    @Expose val avatarUrl: String,
    @Expose val avatarUrlSelected: String
): Parcelable