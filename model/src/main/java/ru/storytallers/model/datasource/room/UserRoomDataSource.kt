package ru.storytallers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytallers.model.datasource.IUserDataSource
import ru.storytallers.model.entity.User
import ru.storytallers.model.entity.room.RoomUser
import ru.storytallers.model.entity.room.db.AppDatabase

class UserRoomDataSource(private val database: AppDatabase): IUserDataSource {

    override fun insertOrReplace(user: User): @NonNull Completable = Completable.fromAction {
        val roomUser = RoomUser(
            user.id,
            user.nickName,
            user.login,
            user.pass,
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
                        roomUser.nickName,
                        roomUser.login,
                        roomUser.pass,
                        roomUser.email,
                        roomUser.avatarUrl,
                        ""
                    )
                )
            } ?: let {
                emitter.onError(RuntimeException("No such user in database"))
            }
        }
}