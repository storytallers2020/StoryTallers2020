package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IStoryDataSource
import ru.storytellers.model.datasource.resourcestorage.storage.CharacterStorage
import ru.storytellers.model.datasource.resourcestorage.storage.LocationStorage
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
    private val locationStorage: LocationStorage,
    private val characterStorage: CharacterStorage
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
                sentence.player?.id ?: 0,
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
            player.character?.id ?: 0,
            storyId
        )

    private fun savePlayers(roomPlayers: List<RoomPlayer>) {
        database.playerDao.insertRange(roomPlayers)
    }

    override fun getStoryById(storyId: Long): Single<Story> =
        Single.create { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                val location = locationStorage.getLocationById(roomStory.locationId)
                Single.fromCallable {
                    emitter.onSuccess(
                        Story(
                            roomStory.id,
                            roomStory.name,
                            roomStory.authors,
                            roomStory.coverUrl,
                            location,
                            null
                        )
                    )
                }
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }

    override fun getStoryWithSentencesById(storyId: Long): Single<Story> =
        Single.create<Story> { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                emitter.onSuccess(mapStory(roomStory))
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }.subscribeOn(Schedulers.io())

    private fun mapStory(roomStory: RoomStory): @NonNull Story {
        val location = locationStorage.getLocationById(roomStory.locationId)
        val sentences = getSentences(roomStory.id)

        return Story(
            roomStory.id,
            roomStory.name,
            roomStory.authors,
            roomStory.coverUrl,
            location,
            sentences
        )
    }

    private fun getSentences(storyId: Long): List<SentenceOfTale>? =
        database.sentenceOfTaleDao
            .getAllStorySentence(storyId)?.map { roomSentence ->
                SentenceOfTale(
                    roomSentence.id,
                    getPlayer(roomSentence.playerId),
                    roomSentence.turn,
                    roomSentence.content,
                    roomSentence.contentType
                )
            }?.sortedWith(compareBy { it.step })

    private fun getPlayer(playerId: Long): Player? =
        database.playerDao.getPlayerById(playerId)?.let { roomPlayer ->
            val character = characterStorage.getCharacterById(roomPlayer.characterId)
            return Player(
                roomPlayer.id,
                roomPlayer.name,
                character
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