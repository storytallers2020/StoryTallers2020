//package ru.storytellers.model.datasource.resourcestorage
//
//import io.reactivex.rxjava3.core.Completable
//import io.reactivex.rxjava3.core.Single
//import io.reactivex.rxjava3.schedulers.Schedulers
//import ru.storytellers.model.datasource.ILocationDataSource
//import ru.storytellers.model.datasource.resourcestorage.storage.LocationStorage
//import ru.storytellers.model.entity.Location
//
//class LocationResDataSource(private val locationStorage: LocationStorage) : ILocationDataSource {
//
//    override fun insertOrReplace(location: Location): Completable =
//        Completable.fromAction {
//            locationStorage.insertOrReplace(location)
//        }.subscribeOn(Schedulers.io())
//
//    override fun insertOrReplace(locationList: List<Location>): Completable =
//        Completable.fromAction {
//            locationStorage.insertOrReplace(locationList)
//        }.subscribeOn(Schedulers.io())
//
//    override fun getLocationById(locationId: Long): Single<Location> =
//    Single.create<Location> { emitter ->
//        locationStorage.getLocationById(locationId)?.let {
//            emitter.onSuccess(it)
//        } ?: let {
//            emitter.onError(RuntimeException("No such location in res"))
//        }
//    }.subscribeOn(Schedulers.io())
//
//    override fun getAll(): Single<List<Location>> =
//        Single.fromCallable { locationStorage.getAll() }
//            .subscribeOn(Schedulers.io())
//
//}