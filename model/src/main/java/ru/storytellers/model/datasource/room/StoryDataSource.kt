package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.datasource.ISentenceOfTaleDataSource
import ru.storytellers.model.datasource.IStoryDataSource
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.model.entity.room.RoomPlayer
import ru.storytellers.model.entity.room.RoomSentenceOfTale
import ru.storytellers.model.entity.room.RoomStory
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.getPlayersFromSentences

class StoryDataSource(
    private val database: AppDatabase,
    private val locationDataSource: ILocationDataSource,
    private val sentenceOfTaleDataSource: ISentenceOfTaleDataSource
) : IStoryDataSource {

    override fun insertOrReplace(story: Story): @NonNull Completable =
        Completable.fromAction {
            val roomStory = RoomStory(
                story.id,
                story.name,
                story.authors,
                story.coverUrl,
                story.location?.id ?: 0
            )
            database.storyDao.insert(roomStory)

            story.sentences?.let {
                saveSentence(story.id, it)
            }
        }

    private fun saveSentence(storyId: Long, sentenceList: List<SentenceOfTale>) {
        val roomSentenceList = sentenceList.map { sentence ->
            RoomSentenceOfTale(
                sentence.id,
                storyId,
                sentence.player.id,
                sentence.step,
                sentence.content,
                sentence.contentType
            )
        }

        val roomPlayerList = sentenceList.getPlayersFromSentences().map {
            mapToRoomPlayer(storyId, it)
        }
        savePlayers(roomPlayerList)

        database.sentenceOfTaleDao.insertRange(roomSentenceList)
    }

    private fun mapToRoomPlayer(storyId: Long, player: Player) =
        RoomPlayer(
            player.id,
            player.name,
            player.character.id,
            storyId
        )

    private fun savePlayers(roomPlayers: List<RoomPlayer>) {
        database.playerDao.insertRange(roomPlayers)
    }

    override fun getStoryById(storyId: Long): Single<Story> =

        Single.create { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                locationDataSource.getLocationById(roomStory.locationId).flatMap {
                    return@flatMap Single.fromCallable {
                        emitter.onSuccess(
                            Story(
                                roomStory.id,
                                roomStory.name,
                                roomStory.authors,
                                roomStory.coverUrl,
                                it,
                                null
                            )
                        )
                    }
                }
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }

    override fun getStoryWithSentencesById(storyId: Long): Single<Story> =

        Single.create { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                mapStory(roomStory).flatMap {story ->
                    Single.fromCallable {  emitter.onSuccess(story) }
                }
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }

    private fun mapStory(roomStory: RoomStory): @NonNull Single<Story> {
        val locationSingle = locationDataSource.getLocationById(roomStory.locationId)
        val sentenceSingle = sentenceOfTaleDataSource.getAllStorySentence(roomStory.id)

        return Single.zip(
            locationSingle,
            sentenceSingle,
            BiFunction<Location, List<SentenceOfTale>, Story> { location, sentences ->
                Story(
                    roomStory.id,
                    roomStory.name,
                    roomStory.authors,
                    roomStory.coverUrl,
                    location,
                    sentences
                )
            }
        )
    }

    override fun getAll(): Single<List<Story>> =
        Single.create { emitter ->
            database.storyDao.getAll()?.let { roomStoryList ->
                val storyList = roomStoryList.map { roomStory ->
                    Story(
                        roomStory.id,
                        roomStory.name,
                        roomStory.authors,
                        roomStory.coverUrl,
                        null,
                        null
                    )
                }
                emitter.onSuccess(storyList)
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }
}