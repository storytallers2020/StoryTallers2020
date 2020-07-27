package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Cover

interface ICoverRepository {
    fun insertOrReplace(cover: Cover): @NonNull Completable
    fun getCoverById(coverId: Long): Single<Cover>
    fun getAll(): Single<List<Cover>>
}
