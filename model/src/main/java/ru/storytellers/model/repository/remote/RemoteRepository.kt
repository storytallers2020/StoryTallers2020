package ru.storytellers.model.repository.remote

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.cache.ICashImageDataSource
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.datasource.room.CoverDataSource
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.toAvatarList
import ru.storytellers.utils.toCachedCoverList
import ru.storytellers.utils.toCashedLocationList

class RemoteRepository(
    private val networkStatus: INetworkStatus,
    private val remoteDataSource: IRemoteDataSource,
    private val characterDataSource: ICharacterDataSource,
    private val locationDataSource: ILocationDataSource,
    private val coverDataSource: CoverDataSource,
    private val cacheImageDataSource: ICashImageDataSource
) : IRemoteRepository {

    override fun cacheCharacters(): Completable =
        networkStatus.isOnlineSingle().flatMapCompletable { isOnline ->
            if (isOnline) {
                remoteDataSource.getCharacters().flatMapCompletable { characterListApi ->
                    cacheImageDataSource.add(characterListApi.characters.toAvatarList())
                    characterDataSource.insertOrReplace(characterListApi.characters)
                }
            } else {
                Completable.complete()
            }
        }.subscribeOn(Schedulers.io())

    override fun cacheLocations(): Completable =
        networkStatus.isOnlineSingle().flatMapCompletable { isOnline ->
            if (isOnline) {
                remoteDataSource.getLocations().flatMapCompletable { locationListApi ->
                    cacheImageDataSource.add(locationListApi.locations.toCashedLocationList())
                    locationDataSource.insertOrReplace(locationListApi.locations)
                }
            } else {
                Completable.complete()
            }
        }.subscribeOn(Schedulers.io())

    override fun cacheCovers(): Completable =
        networkStatus.isOnlineSingle().flatMapCompletable { isOnline ->
            if (isOnline) {
                remoteDataSource.getCovers().flatMapCompletable { coverListApi ->
                    cacheImageDataSource.add(coverListApi.covers.toCachedCoverList())
                    coverDataSource.insertOrReplace(coverListApi.covers)
                }
            } else {
                Completable.complete()
            }
        }.subscribeOn(Schedulers.io())

}