package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.entity.Location
import ru.storytellers.utils.NetworkStatus

class LocationRepository(
    //private val networkStatus: NetworkStatus,
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocationDataSource
): ILocationRepository {

    override fun insertOrReplace(location: Location): @NonNull Completable =
        localDataSource.insertOrReplace(location)
            .subscribeOn(Schedulers.io())

    override fun getLocationById(locationId: Long): Single<Location> =
        localDataSource.getLocationById(locationId)
            .subscribeOn(Schedulers.io())

//    override fun getAll(): Single<List<Location>> =
//        localDataSource.getAll()
//            .subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Location>> =
//    networkStatus.isOnlineSingle().flatMap { isOnline ->
//        if (isOnline) {
            remoteDataSource.getLocations().flatMap { locationListApi ->
                val BASE_URL = "http://188.225.25.249/media/"
                val locationList = locationListApi.locations.map {
                    Location(
                        it.id.toLong(),
                        it.name,
                        BASE_URL + it.imageUrl,
                        BASE_URL + it.imageForRecycler,
                        it.descriptions
                    )
                }
                localDataSource.insertOrReplace(locationList).toSingleDefault(locationList)
            //}
//        } else {
//            localDataSource.getAll()
//        }
    }.subscribeOn(Schedulers.io())

}
