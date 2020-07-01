package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.ISentenceOfTaleDataSource
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.room.RoomSentenceOfTale
import ru.storytellers.model.entity.room.db.AppDatabase

class SentenceOfTaleDataSource(
    private val database: AppDatabase,
    private val characterDataSource: ICharacterDataSource
) : ISentenceOfTaleDataSource {

    override fun insertOrReplace(sentenceOfTale: SentenceOfTale): @NonNull Completable =
        Completable.fromAction {
            val roomSentence = RoomSentenceOfTale(
                sentenceOfTale.id,
                sentenceOfTale.storyId,
                sentenceOfTale.character?.id ?: 0,
                sentenceOfTale.step,
                sentenceOfTale.content,
                sentenceOfTale.contentType
            )
            database.sentenceOfTaleDao.insert(roomSentence)
        }

    override fun getSentenceById(sentenceId: Long): Single<SentenceOfTale> =
        Single.create { emitter ->
            database.sentenceOfTaleDao.getSentenceById(sentenceId)?.let { roomSentence ->
                characterDataSource.getCharacterById(roomSentence.characterId)
                    .flatMap { character ->
                        return@flatMap Single.fromCallable {
                            emitter.onSuccess(
                                SentenceOfTale(
                                    roomSentence.id,
                                    roomSentence.storyId,
                                    character,
                                    roomSentence.step,
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
            database.sentenceOfTaleDao.getAllStorySentence(storyId)?.let { roomSentenceList ->

                val sentenceList = roomSentenceList.map { roomSentence ->
                    val character = database.characterDao.getCharacterById(roomSentence.characterId)
                        ?.let { roomCharacter ->
                            Character(
                                roomCharacter.id,
                                roomCharacter.name,
                                roomCharacter.avatarUrl
                            )
                        }

                    SentenceOfTale(
                        roomSentence.id,
                        roomSentence.storyId,
                        character,
                        roomSentence.step,
                        roomSentence.content,
                        roomSentence.contentType
                    )

                }
                emitter.onSuccess(sentenceList)
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }
}