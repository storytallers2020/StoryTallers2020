package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ICoverDataSource
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.*

class CoverDataSource(private val database: AppDatabase) : ICoverDataSource {

    override fun insertOrReplace(cover: Cover): Completable =
        Completable.fromAction {
            database.coverDao.insert(cover.toRoomCover())
        }

    override fun insertOrReplace(coverList: List<Cover>): Completable =
        Completable.fromAction {
            database.coverDao.insert(coverList.toRoomCoverList())
        }


    override fun getCoverById(coverId: Long): Single<Cover> =
        Single.create { emitter ->
            database.coverDao.getCoverById(coverId)?.let {
                emitter.onSuccess(it.toCover())
            } ?: let {
                emitter.onError(RuntimeException("No such cover in resource"))
            }
        }

    override fun getAll(): Single<List<Cover>> =
        Single.create { emitter ->
            database.coverDao.getAll()?.let { roomCoverList ->
                emitter.onSuccess(roomCoverList.toCoverList())
            } ?: let {
                emitter.onError(RuntimeException("No such cover in database"))
            }
        }
}