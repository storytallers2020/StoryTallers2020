package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IVersionDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.entity.Versions
import ru.storytellers.model.entity.VersionsComparator
import ru.storytellers.utils.toVersions

class VersionRepository(
    private val localDataSource: IVersionDataSource,
    private val remoteDataSource: IRemoteDataSource,
) : IVersionRepository {

    override fun insertOrReplace(version: Versions): Completable =
        localDataSource.insertOrReplace(version)
            .subscribeOn(Schedulers.io())

    override fun getVersions(): Single<VersionsComparator> =
        remoteDataSource.getVersions().flatMap { versionApiList ->
            localDataSource.getAll().flatMap { localVersion ->
                Single.just(VersionsComparator(
                    versionApiList.versions[0].toVersions(),
                    localVersion
                ))
            }
        }.subscribeOn(Schedulers.io())
}
