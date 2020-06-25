package ru.storytellers.model.entity

class SentenceOfTale (
    val id: Long,
    val storyId: Long,
    val character: Character?,
    val step: Int,
    val content: String,
    val contentType: String
)