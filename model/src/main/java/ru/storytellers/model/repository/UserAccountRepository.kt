package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.datasource.remote.api.UserApi
import ru.storytellers.model.entity.User
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.convertUserToUserApi

class UserAccountRepository(
    private val networkStatus: INetworkStatus,
    private val remoteDataSource: IRemoteDataSource
) : IUserAccountRepository {

    override fun saveUser(user: User): Completable =
        networkStatus.isOnlineSingle().flatMapCompletable { isOnline ->
            if (isOnline)
                remoteDataSource.saveUser(convertUserToUserApi(user))
            else
                Completable.error(Throwable("No network"))
        }.subscribeOn(Schedulers.io())

    override fun getUser(user: User): Single<UserApi> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
                if (isOnline)
                    remoteDataSource.getUserById(user.id)
                else Single.error(Throwable("No network"))
        }.subscribeOn(Schedulers.io())

    /*
    override fun getUser(user: User): Single<Long> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            Single.create<Long> { emitter ->
                if (isOnline) {
                    var usId = 0L
                    remoteDataSource.getUserId(user.id)
                        .map { userApi ->
                            usId = userApi.userId
                        }
                    emitter.onSuccess(usId)
                }
                else emitter.onError(Throwable("No network"))
            }
        }.subscribeOn(Schedulers.io())



     */
}