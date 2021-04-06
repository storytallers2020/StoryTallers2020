package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.IWordDataSource
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.toRoomWordList
import ru.storytellers.utils.toWordList

class WordDataSource(private val database: AppDatabase) : IWordDataSource {

    override fun insertOrReplace(wordList: List<String>, lang: String): Completable  =
        Completable.fromAction {
            database.wordDao.insert(wordList.toRoomWordList(lang))
        }

    override fun getAll(lang: String): Single<List<String>> =
        Single.create { emitter ->
            database.wordDao.getAll(lang)?.let { roomWordList ->
                emitter.onSuccess(roomWordList.toWordList())
            } ?: let {
                emitter.onError(RuntimeException("No such word in database"))
            }
        }
}