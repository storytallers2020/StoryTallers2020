package ru.storytellers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Versions

interface IVersionDataSource {
    fun insertOrReplace(version: Versions): @NonNull Completable
    fun getAll(): Single<Versions>
}