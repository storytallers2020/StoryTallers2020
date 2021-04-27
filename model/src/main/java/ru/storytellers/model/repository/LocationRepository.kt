package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.entity.Location

class LocationRepository(
    private val localDataSource: ILocationDataSource,
) : ILocationRepository {

    override fun insertOrReplace(location: Location): @NonNull Completable =
        localDataSource.insertOrReplace(location)
            .subscribeOn(Schedulers.io())

    override fun getLocationById(locationId: Long): Single<Location> =
        localDataSource.getLocationById(locationId)
            .subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Location>> =
        localDataSource.getAll()
            .subscribeOn(Schedulers.io())
}