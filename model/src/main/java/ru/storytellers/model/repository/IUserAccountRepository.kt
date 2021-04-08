package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.jetbrains.annotations.NotNull
import ru.storytellers.model.datasource.remote.api.UserApi
import ru.storytellers.model.entity.User

interface IUserAccountRepository {
    fun saveUser(user : User) : @NotNull Completable
    fun getUser(user : User) : Single<UserApi>
}