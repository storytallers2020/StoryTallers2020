package ru.storytellers.model.repository.remote

import io.reactivex.rxjava3.core.Completable

interface IRemoteRepository {
    fun cacheCharacters(): Completable
}
