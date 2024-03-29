package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Location

interface ILocationRepository {
    fun insertOrReplace(location: Location): @NonNull Completable
    fun getLocationById(locationId: Long): Single<Location>
    fun getAll(): Single<List<Location>>
}
