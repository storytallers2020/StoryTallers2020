package ru.storytellers.model.entity

class Story (
    val id: Long,
    val name: String,
    val authors: String,
    val coverUrl: String,
    val location: Location?
)