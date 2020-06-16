package com.google.vitaly.model.userdata

class Story(
    val storyId: Int?,
    val storyName: String?,
    val storyLocation: Location?,
    val storyPersonage: List<Personage> = listOf()
)