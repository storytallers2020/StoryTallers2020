package ru.storytallers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytallers.model.entity.User

interface IUserDataSource {
    fun insertOrReplace(user: User): @NonNull Completable
    fun getUserById(userId: Long): Single<User>
}