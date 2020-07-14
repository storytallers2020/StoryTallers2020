package ru.storytellers.model.entity

data class SentenceOfTale (
    val id: Long,
    val player: Player,
    val step: Int,
    val content: String,
    val contentType: ContentTypeEnum
)