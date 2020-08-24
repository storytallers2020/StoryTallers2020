package ru.storytellers.utils

import io.reactivex.rxjava3.annotations.NonNull
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.model.entity.room.RoomStory

fun Story.toRoomStory(): RoomStory =
    RoomStory(
        this.id,
        this.name,
        this.authors,
        this.coverUrl,
        this.location?.id ?: 0
    )

fun RoomStory.toStory(location: Location?, sentences: List<SentenceOfTale>?): @NonNull Story =
    Story(
        this.id,
        this.name,
        this.authors,
        this.coverUrl,
        location,
        sentences
    )