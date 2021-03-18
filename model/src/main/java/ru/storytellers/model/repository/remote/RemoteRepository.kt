package ru.storytellers.model.repository.remote

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.cache.ICashImageDataSource
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.toAvatarList

class RemoteRepository(
    private val networkStatus: INetworkStatus,
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ICharacterDataSource,
    private val cacheImageDataSource: ICashImageDataSource
) : IRemoteRepository {

    override fun cacheCharacters(): Completable =
        networkStatus.isOnlineSingle().flatMapCompletable { isOnline ->
            if (isOnline) {
                remoteDataSource.getCharacters().flatMapCompletable { characterListApi ->
                    cacheImageDataSource.add(characterListApi.characters.toAvatarList())
                    localDataSource.insertOrReplace(characterListApi.characters)
                }
            } else {
                Completable.complete()
            }
        }.subscribeOn(Schedulers.io())
}