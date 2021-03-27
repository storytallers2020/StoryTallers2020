package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ICoverDataSource
import ru.storytellers.model.entity.Cover

class CoverRepository(
    private val localDataSource: ICoverDataSource,
) : ICoverRepository {

    override fun insertOrReplace(cover: Cover): @NonNull Completable =
        localDataSource.insertOrReplace(cover)
            .subscribeOn(Schedulers.io())

    override fun getCoverById(coverId: Long): Single<Cover> =
        localDataSource.getCoverById(coverId)
            .subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Cover>> =
        localDataSource.getAll()
            .subscribeOn(Schedulers.io())
}