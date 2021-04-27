package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IStoryDataSource
import ru.storytellers.model.entity.*
import ru.storytellers.model.entity.room.RoomPlayer
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.*

class StoryDataSource(private val database: AppDatabase) : IStoryDataSource {

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

    override fun deleteStoryById(storyId: Long): Single<Int> =
        Single.create { emitter ->
            try {
                val count = database.storyDao.deleteById(storyId)
                emitter.onSuccess(count)
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
    override fun updateTitleStory(titleStory: String, storyId: Long):Single<Int> =
        Single.create { emitter ->
            try {
                val count = database.storyDao.updateTitleStory(titleStory, storyId)
                emitter.onSuccess(count)
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }

    override fun getStoryById(storyId: Long): Single<Story> =
        Single.create { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                val location = getLocation(roomStory.locationId)

                emitter.onSuccess(roomStory.toStory(location, null))
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }

    private fun getLocation(locationId: Long): Location? =
        database.locationDao.getLocationById(locationId)?.toLocation()

    override fun getStoryWithSentencesById(storyId: Long): Single<Story> =
        Single.create<Story> { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                val location = getLocation(roomStory.locationId)
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
            val character = getCharacter(roomPlayer.characterId)
            roomPlayer.toPlayer(character)
        }

    private fun getCharacter(characterId: Long): Character? =
        database.characterDao.getCharacterById(characterId)?.toCharacter()

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