package ru.storytellers.model.entity

import com.google.gson.annotations.Expose

data class Cover(
    @Expose val id: Long,
    @Expose val name: String,
    @Expose val imageUrl: String,
    @Expose val imagePreview: String
)