package ru.storytellers.model.userdata

class StoryOld(
    val id: Int?,
    val name: String?,
    val location: LocationOld?,
    val personage: List<PersonageOld> = listOf()
)