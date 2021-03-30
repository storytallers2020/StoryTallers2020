package ru.storytellers.model.repository.remote

import io.reactivex.rxjava3.core.Completable

interface IRemoteRepository {
    fun cacheCharacters(): Completable
    fun cacheLocations(): Completable
    fun cacheCovers(): Completable
    fun cacheWords(lang: String): Completable
}
