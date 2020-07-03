package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.room.RoomLocation
import ru.storytellers.model.entity.room.db.AppDatabase

class LocationDataSource(private val database: AppDatabase) : ILocationDataSource {

    override fun insertOrReplace(location: Location): @NonNull Completable =
        Completable.fromAction {
            val roomLocation = RoomLocation(
                location.id,
                location.name,
                location.imageUrl,
                location.descriptions
            )
            database.locationDao.insert(roomLocation)
        }

    override fun getLocationById(locationId: Long): Single<Location> =
        Single.create { emitter ->
            database.locationDao.getLocationById(locationId)?.let { roomLocation ->
                emitter.onSuccess(
                    Location(
                        roomLocation.id,
                        roomLocation.name,
                        roomLocation.imageUrl,
                        roomLocation.description
                    )
                )
            } ?: let {
                emitter.onError(RuntimeException("No such location in database"))
            }
        }

    override fun getAll(): Single<List<Location>> =
        Single.create { emitter ->
            database.locationDao.getAll()?.let { roomLocationList ->
                val locationList = roomLocationList.map {
                    Location(
                        it.id,
                        it.name,
                        it.imageUrl,
                        it.description
                    )
                }
                emitter.onSuccess(locationList)
            } ?: let {
                emitter.onError(RuntimeException("No such location in database"))
            }
        }
}