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
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.*

class StoryDataSource(
    private val database: AppDatabase,
    private val locationStorage: LocationStorage,
    private val characterStorage: CharacterStorage
) : IStoryDataSource {

    override fun insertOrReplace(story: Story): @NonNull Completable =
        Completable.fromAction {
            val roomStory = story.toRoomStory()
            database.storyDao.insert(roomStory)

            story.sentences?.let {
                saveSentence(story.id, it)
            }
        }

    private fun saveSentence(storyId: Long, sentenceList: List<SentenceOfTale>) {
        val roomSentenceList = sentenceList.toRoomSentences(storyId)

        val roomPlayerList = sentenceList
            .getPlayersFromSentences()
            .map { player -> player.toRoomPlayer(storyId) }

        savePlayers(roomPlayerList)

        database.sentenceOfTaleDao.insertRange(roomSentenceList)
    }

    private fun savePlayers(roomPlayers: List<RoomPlayer>) {
        database.playerDao.insertRange(roomPlayers)
    }

    override fun deleteStory(story: Story): Single<Int> =
        Single.create<Int> { emitter ->
            try {
                story.sentences?.let { sentences -> deleteSentences(story.id, sentences) }
                val count = database.storyDao.delete(story.toRoomStory())
                Single.fromCallable { emitter.onSuccess(count) }
            } catch (e: Throwable) {
                Single.fromCallable { emitter.onError(e) }
            }
        }

    private fun deleteSentences(storyId: Long, sentences: List<SentenceOfTale>) {
        val players = sentences.getPlayersFromSentences()
        database.sentenceOfTaleDao.delete(sentences.toRoomSentences(storyId))
        database.playerDao.delete(players.toRoomPlayers(storyId))
    }

    override fun getStoryById(storyId: Long): Single<Story> =
        Single.create { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                val location = locationStorage.getLocationById(roomStory.locationId)
                Single.fromCallable {
                    emitter.onSuccess(roomStory.toStory(location, null))
                }
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }

    override fun getStoryWithSentencesById(storyId: Long): Single<Story> =
        Single.create<Story> { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                val location = locationStorage.getLocationById(roomStory.locationId)
                val sentences = getSentences(roomStory.id)

                emitter.onSuccess(roomStory.toStory(location, sentences))
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }.subscribeOn(Schedulers.io())

    private fun getSentences(storyId: Long): List<SentenceOfTale>? =
        database.sentenceOfTaleDao
            .getAllStorySentence(storyId)?.map { roomSentence ->
                val player = getPlayer(roomSentence.playerId)
                roomSentence.toSentence(player)
            }?.sortedWith(compareBy { it.step })

    private fun getPlayer(playerId: Long): Player? =
        database.playerDao.getPlayerById(playerId)?.let { roomPlayer ->
            val character = characterStorage.getCharacterById(roomPlayer.characterId)
            roomPlayer.toPlayer(character)
        }

    override fun getAll(): Single<List<Story>> =
        Single.create { emitter ->
            database.storyDao.getAll()?.let { roomStoryList ->
                val storyList = roomStoryList.map { roomStory ->
                    roomStory.toStory(null, null)
                }
                emitter.onSuccess(storyList)
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }

}