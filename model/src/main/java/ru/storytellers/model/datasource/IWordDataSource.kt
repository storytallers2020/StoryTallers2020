package ru.storytellers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IWordDataSource {
    fun insertOrReplace(wordList: List<String>, lang: String): @NonNull Completable
    fun getAll(language: String): Single<List<String>>
}