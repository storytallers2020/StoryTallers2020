package ru.storytellers.model.entity

data class Versions (
    val id: Int,
    var characterVersion: Int,
    var locationVersion: Int,
    var coverVersion: Int,
    var rusWordVersion: Int
)