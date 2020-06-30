package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ISentenceOfTaleDataSource
import ru.storytellers.model.entity.SentenceOfTale

class SentenceOfTaleRepository(private val localDataSource: ISentenceOfTaleDataSource) :
    ISentenceOfTaleRepository {

        override fun insertOrReplace(sentenceOfTale: SentenceOfTale): @NonNull Completable =
            localDataSource.insertOrReplace(sentenceOfTale)
                .subscribeOn(Schedulers.io())

        override fun getSentenceById(sentenceId: Long): Single<SentenceOfTale> =
            localDataSource.getSentenceById(sentenceId)
                .subscribeOn(Schedulers.io())

        override fun getAllStorySentence(storyId: Long): Single<List<SentenceOfTale>> =
            localDataSource.getAllStorySentence(storyId)
                .subscribeOn(Schedulers.io())

}
