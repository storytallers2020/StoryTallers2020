package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IUserRoomDataSource
import ru.storytellers.model.entity.User

class UserLocalRepository(private val IUserRoomDataSource: IUserRoomDataSource): IUserLocalRepository {

    override fun addUser(user: User): Completable =
        IUserRoomDataSource.insertOrReplace(user)
            .subscribeOn(Schedulers.io())

    override fun getUserById(userId: Long): Single<User>  =
        IUserRoomDataSource.getUserById(userId)
            .subscribeOn(Schedulers.io())

}
