package ru.storytellers.model.datasource.resourcestorage

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ICoverDataSource
import ru.storytellers.model.datasource.resourcestorage.storage.CoverStorage
import ru.storytellers.model.entity.Cover

class CoverResDataSource(private val coverStorage: CoverStorage) : ICoverDataSource {

    override fun insertOrReplace(cover: Cover): Completable =
        Completable.fromAction {
            coverStorage.insertOrReplace(cover)
        }

    override fun insertOrReplace(coverList: List<Cover>): Completable =
        Completable.fromAction {
            coverStorage.insertOrReplace(coverList)
        }.subscribeOn(Schedulers.io())


    override fun getCoverById(coverId: Long): Single<Cover> =
        Single.create { emitter ->
            coverStorage.getCoverById(coverId)?.let {
                emitter.onSuccess(it)
            } ?: let {
                emitter.onError(RuntimeException("No such cover in resource"))
            }
        }

    override fun getAll(): Single<List<Cover>> =
        Single.create { emitter ->
            emitter.onSuccess(coverStorage.getAll())
        }

}