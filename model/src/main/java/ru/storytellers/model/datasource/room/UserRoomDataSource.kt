package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IUserRoomDataSource
import ru.storytellers.model.entity.User
import ru.storytellers.model.entity.room.RoomUser
import ru.storytellers.model.entity.room.db.AppDatabase

class UserRoomDataSource(private val database: AppDatabase): IUserRoomDataSource {

    override fun insertOrReplace(user: User): @NonNull Completable = Completable.fromAction {
        val roomUser = RoomUser(
            user.id,
            user.name,
            user.email,
            user.avatarUrl
        )
        database.userDao.insert(roomUser)
    }.subscribeOn(Schedulers.io())

    override fun getUserById(userId: Long): Single<User> =

        Single.create { emitter ->
            database.userDao.getUserById(userId)?.let { roomUser ->
                emitter.onSuccess(
                    User(
                        roomUser.id,
                        roomUser.name,
                        roomUser.email,
                        roomUser.avatarUrl
                    )
                )
            } ?: let {
                emitter.onError(RuntimeException("No such user in database"))
            }
        }
}