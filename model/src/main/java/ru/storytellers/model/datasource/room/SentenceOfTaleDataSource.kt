package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.IPlayerDataSource
import ru.storytellers.model.datasource.ISentenceOfTaleDataSource
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.room.RoomSentenceOfTale
import ru.storytellers.model.entity.room.db.AppDatabase

class SentenceOfTaleDataSource(
    private val database: AppDatabase,
    private val playerDataSource: IPlayerDataSource
) : ISentenceOfTaleDataSource {

    override fun insertOrReplace(
        storyId: Long,
        sentenceOfTale: SentenceOfTale
    ): @NonNull Completable =
        Completable.fromAction {
            val roomSentence = RoomSentenceOfTale(
                sentenceOfTale.id,
                storyId,
                sentenceOfTale.player.id,
                sentenceOfTale.step,
                sentenceOfTale.content,
                sentenceOfTale.contentType
            )
            database.sentenceOfTaleDao.insert(roomSentence)
        }

    override fun getSentenceById(sentenceId: Long): Single<SentenceOfTale> =
        Single.create { emitter ->
            database.sentenceOfTaleDao.getSentenceById(sentenceId)?.let { roomSentence ->
                playerDataSource.getPlayerById(roomSentence.playerId)
                    .flatMap { player ->
                        return@flatMap Single.fromCallable {
                            emitter.onSuccess(
                                SentenceOfTale(
                                    roomSentence.id,
                                    player,
                                    roomSentence.turn,
                                    roomSentence.content,
                                    roomSentence.contentType
                                )
                            )
                        }
                    }
            } ?: let {
                emitter.onError(RuntimeException("No such sentence in database"))
            }
        }

    override fun getAllStorySentence(storyId: Long): Single<List<SentenceOfTale>> =
        Single.create { emitter ->
            val sentenceList = ArrayList<SentenceOfTale>()

            database.sentenceOfTaleDao.getAllStorySentence(storyId)?.let { roomSentenceList ->
                roomSentenceList.map { roomSentence ->
                    playerDataSource.getPlayerById(roomSentence.playerId)
                        .flatMap { player ->
                            return@flatMap Single.fromCallable {
                                SentenceOfTale(
                                    roomSentence.id,
                                    player,
                                    roomSentence.turn,
                                    roomSentence.content,
                                    roomSentence.contentType
                                )
                            }
                        }.map {
                            sentenceList.add(it)
                        }
                }

                emitter.onSuccess(sentenceList)
            } ?: let {
                emitter.onError(RuntimeException("No such sentence in database"))
            }
        }


}