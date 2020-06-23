package ru.storytellers.model.entity.userdata

class Story(
    val id: Int?,
    val name: String?,
    val location: Location?,
    val personage: List<Personage> = listOf()
)