package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Versions
import ru.storytellers.model.entity.VersionsComparator

interface IVersionRepository {
    fun insertOrReplace(version: Versions): @NonNull Completable
    fun getVersions(): Single<VersionsComparator>
}
