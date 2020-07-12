package ru.storytellers.engine

import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.User

data class Player(
    val id: Long,
    val name: String,
    val user: User,
    val avatarUrl: String,
    val character: Character,
    val isActive: Boolean=false // признак того игрока чей ход в данный момент
    )