package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.SentenceOfTale

interface ISentenceOfTaleRepository {
    fun insertOrReplace(sentenceOfTale: SentenceOfTale): @NonNull Completable
    fun getSentenceById(sentenceId: Long): Single<SentenceOfTale>
    fun getAllStorySentence(storyId: Long): Single<List<SentenceOfTale>>
}
