package com.google.vitaly.model.userdata

class Story(
    val id: Int?,
    val name: String?,
    val location: Location?,
    val personage: List<Personage> = listOf()
)