package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IVersionDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.entity.Versions
import ru.storytellers.model.entity.VersionsComparator
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.toVersions

class VersionRepository(
    private val networkStatus: INetworkStatus,
    private val localDataSource: IVersionDataSource,
    private val remoteDataSource: IRemoteDataSource,
) : IVersionRepository {

    override fun insertOrReplace(version: Versions): Completable =
        localDataSource.insertOrReplace(version)
            .subscribeOn(Schedulers.io())

    override fun getVersions(): Single<VersionsComparator> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) { // Если online загружаем версии из сети и бд
                remoteDataSource.getVersions().flatMap { versionApiList ->
                    localDataSource.getAll().flatMap { localVersion ->
                        Single.just(
                            VersionsComparator(
                                versionApiList.versions[0].toVersions(),
                                localVersion
                            )
                        )
                    }
                }
            } else { // Если offline версию из сети не загружаем. Имитируем, что все хорошо
                // Возможно в дальнейшем тут лучше вернуть ошибку и проверять есть ли ресурсы
                localDataSource.getAll().flatMap { localVersion ->
                    Single.just(
                        VersionsComparator(
                            Versions(0, 0,0, 0, 0),
                            localVersion
                        )
                    )
                }
            }
        }.subscribeOn(Schedulers.io())

}