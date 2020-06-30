package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.User

interface IUserRepository {
    fun addUser(user: User): @NonNull Completable
    fun getUserById(userId: Long): Single<User>
}
