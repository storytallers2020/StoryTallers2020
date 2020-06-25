package ru.storytellers.model.datasource

import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.SentenceOfTale

interface ISentenceOfTaleDataSource {
    fun insertOrReplace(sentenceOfTale: SentenceOfTale)
    fun getSentenceById(sentenceId: Long): Single<SentenceOfTale>
    fun getAllStorySentence(storyId: Long): Single<List<SentenceOfTale>>
}