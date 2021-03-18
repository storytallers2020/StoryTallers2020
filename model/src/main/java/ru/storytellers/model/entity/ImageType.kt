package ru.storytellers.model.entity

import ru.storytellers.model.R

enum class ImageType(val resourceId: Int) {
    AVATAR(R.drawable.avatar_stub),
    AVATAR_SELECTED(R.drawable.avatar_selected_stub),
    LOCATION_RECYCLER(R.drawable.location_recycler_stub),
    LOCATION(R.drawable.location_stub),
    COVER_RECYCLER(R.drawable.cover_recycler_stub)
}