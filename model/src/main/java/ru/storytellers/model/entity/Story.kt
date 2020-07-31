package ru.storytellers.model.entity

data class Story (
    val id: Long,
    var name: String,
    var authors: String,
    var coverUrl: String,
    var location: Location?,
    var sentences: List<SentenceOfTale>?
)