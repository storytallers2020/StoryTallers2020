package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.cache.ICashImageDataSource
import ru.storytellers.model.datasource.ICoverDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.toCachedCoverList

class CoverRepository(
    private val networkStatus: INetworkStatus,
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ICoverDataSource,
    private val cashImageDataSource: ICashImageDataSource
): ICoverRepository {

    override fun insertOrReplace(cover: Cover): @NonNull Completable =
        localDataSource.insertOrReplace(cover)
            .subscribeOn(Schedulers.io())

    override fun getCoverById(coverId: Long): Single<Cover> =
        localDataSource.getCoverById(coverId)
            .subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Cover>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                remoteDataSource.getCovers().flatMap { coverListApi ->
                    cashImageDataSource.add(coverListApi.covers.toCachedCoverList())
                    localDataSource
                        .insertOrReplace(coverListApi.covers)
                        .toSingleDefault(coverListApi.covers)
                }
            } else {
                localDataSource.getAll()
            }
        }.subscribeOn(Schedulers.io())
}