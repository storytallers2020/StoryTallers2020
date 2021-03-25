package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.jetbrains.annotations.NotNull
import ru.storytellers.model.entity.UserAccount

interface IUserAccountRepository {
    fun saveUserAccount(userAccount : UserAccount) : Single<UserAccount>
    fun saveUserAccountCompletable(userAccount : UserAccount) : @NotNull Completable
}