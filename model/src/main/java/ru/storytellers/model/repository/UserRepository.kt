package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IUserDataSource
import ru.storytellers.model.entity.User

class UserRepository(private val localDataSource: IUserDataSource): IUserRepository {

    override fun addUser(user: User): Completable =
        localDataSource.insertOrReplace(user)
            .subscribeOn(Schedulers.io())

    override fun getUserById(userId: Long): Single<User>  =
        localDataSource.getUserById(userId)
            .subscribeOn(Schedulers.io())

}