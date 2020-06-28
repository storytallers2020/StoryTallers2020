package ru.storytallers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytallers.model.entity.Location

interface ILocationDataSource {
    fun insertOrReplace(location: Location): @NonNull Completable
    fun getLocationById(locationId: Long): Single<Location>
    fun getAll(): Single<List<Location>>
}