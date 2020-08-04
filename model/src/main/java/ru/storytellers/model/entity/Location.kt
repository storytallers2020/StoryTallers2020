package ru.storytellers.model.entity

data class Location(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val imageForRecycler: String,
    val descriptions: String
)