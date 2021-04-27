package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IWordDataSource

class WordRepository(
    private val localDataSource: IWordDataSource,
): IWordRepository {
    override fun getAll(lang: String): Single<List<String>> =
        localDataSource.getAll(lang)
            .subscribeOn(Schedulers.io())
}