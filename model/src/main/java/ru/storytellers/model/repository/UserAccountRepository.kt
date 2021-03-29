package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.entity.UserAccount
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.convertAccountToAccountApi

class UserAccountRepository(
    private val networkStatus: INetworkStatus,
    private val remoteDataSource: IRemoteDataSource
) : IUserAccountRepository {

    override fun saveUser(userAccount: UserAccount): Completable =
        networkStatus.isOnlineSingle().flatMapCompletable { isOnline ->
            if (isOnline)
                remoteDataSource.saveUser(convertAccountToAccountApi(userAccount))
            else
                Completable.error(Throwable("No network"))
        }.subscribeOn(Schedulers.io())
}