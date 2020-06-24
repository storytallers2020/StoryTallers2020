package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.IUserDataSource
import ru.storytellers.model.entity.User
import ru.storytellers.model.entity.room.RoomUser
import ru.storytellers.model.entity.room.db.AppDatabase

class UserRoomDataSource(private val database: AppDatabase): IUserDataSource {
    override fun insertOrReplace(user: User) {
        val roomUser = RoomUser(
            user.id,
            user.nickName,
            user.login,
            user.pass,
            user.email,
            user.avatarUrl
        )
        database.userDao.insert(roomUser)
    }

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