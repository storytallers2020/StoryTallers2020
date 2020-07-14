package ru.storytellers.model.entity

data class Story (
    val id: Long,
    val name: String,
    val authors: String,
    val coverUrl: String,
    val location: Location?
)