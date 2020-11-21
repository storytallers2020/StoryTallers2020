package ru.storytellers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable

interface ICashImageDataSource {
    fun add(urlList: List<String>): @NonNull Completable
    //fun getImagePathByUrl(url: Url): Single<Url>
}