package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.IVersionDataSource
import ru.storytellers.model.entity.Versions
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.defaultVersions
import ru.storytellers.utils.toRoomVersion
import ru.storytellers.utils.toVersions

class VersionDataSource(private val database: AppDatabase) : IVersionDataSource {

    override fun insertOrReplace(version: Versions): Completable =
        Completable.fromAction {
            database.versionsDao.insert(version.toRoomVersion())
        }

    override fun getAll(): Single<Versions> =
        Single.create { emitter ->
            database.versionsDao.getAll()?.let { roomVersion ->
                emitter.onSuccess(roomVersion.toVersions())
            } ?: let {
                emitter.onSuccess(defaultVersions())
            }
        }
}