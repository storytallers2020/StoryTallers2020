package ru.storytellers.model.datasource

import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.User

interface IUserDataSource {
    fun insertOrReplace(user: User)
    fun getUserById(userId: Long): Single<User>
}