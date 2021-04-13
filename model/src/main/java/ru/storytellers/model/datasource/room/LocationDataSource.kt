package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.toLocation
import ru.storytellers.utils.toLocationList
import ru.storytellers.utils.toRoomLocation
import ru.storytellers.utils.toRoomLocationList

class LocationDataSource(private val database: AppDatabase) : ILocationDataSource {

    override fun insertOrReplace(location: Location): @NonNull Completable =
        Completable.fromAction {
            database.locationDao.insert(location.toRoomLocation())
        }

    override fun insertOrReplace(locationList: List<Location>): Completable =
        Completable.fromAction {
            database.locationDao.insert(locationList.toRoomLocationList())
        }

    override fun getLocationById(locationId: Long): Single<Location> =
        Single.create { emitter ->
            database.locationDao.getLocationById(locationId)?.let { roomLocation ->
                emitter.onSuccess(roomLocation.toLocation())
            } ?: let {
                emitter.onError(RuntimeException("No such location in database"))
            }
        }

    override fun getAll(): Single<List<Location>> =
        Single.create { emitter ->
            database.locationDao.getAll()?.let { roomLocationList ->
                emitter.onSuccess(roomLocationList.toLocationList())
            } ?: let {
                emitter.onError(RuntimeException("No such location in database"))
            }
        }

}