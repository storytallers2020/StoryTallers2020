package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.IPlayerDataSource
import ru.storytellers.model.datasource.ISentenceOfTaleDataSource
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.room.RoomSentenceOfTale
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.toRoomSentenceOfTale
import ru.storytellers.utils.toSentence

class SentenceOfTaleDataSource(
    private val database: AppDatabase,
    private val playerDataSource: IPlayerDataSource
) : ISentenceOfTaleDataSource {

    override fun insertOrReplace(
        storyId: Long,
        sentenceOfTale: SentenceOfTale
    ): @NonNull Completable =
        Completable.fromAction {
            val roomSentence = sentenceOfTale.toRoomSentenceOfTale(storyId)
            database.sentenceOfTaleDao.insert(roomSentence)
        }

    override fun getSentenceById(sentenceId: Long): Single<SentenceOfTale> =
        Single.create { emitter ->
            database.sentenceOfTaleDao.getSentenceById(sentenceId)?.let { roomSentence ->
                playerDataSource.getPlayerById(roomSentence.playerId)
                    .flatMap { player ->
                        return@flatMap Single.fromCallable {
                            emitter.onSuccess(roomSentence.toSentence(player))
                        }
                    }
            } ?: let {
                emitter.onError(RuntimeException("No such sentence in database"))
            }
        }

    override fun getAllStorySentence(storyId: Long): Single<List<SentenceOfTale>> =
        Single.create { emitter ->
            database.sentenceOfTaleDao.getAllStorySentence(storyId)?.let { roomSentenceList ->
                mapSentences(roomSentenceList).flatMap { sentences ->
                    return@flatMap Single.fromCallable { emitter.onSuccess(sentences) }
                } ?: let {
                    emitter.onError(RuntimeException("No such sentences in database"))
                }
            }
        }

    private fun mapSentences(roomSentenceList: List<RoomSentenceOfTale>):
            @NonNull Single<List<SentenceOfTale>> =
        playerDataSource.getAll().flatMap { playerList ->
            val sentences = roomSentenceList.map { roomSentence ->
                val player = playerList.find { it.id == roomSentence.playerId }
                roomSentence.toSentence(player)
            }
            return@flatMap Single.fromCallable { sentences }
        }

}