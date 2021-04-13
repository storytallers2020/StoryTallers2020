package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Single

interface IWordRepository {
    fun getAll(lang: String): Single<List<String>>
}
