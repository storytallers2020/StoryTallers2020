package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.IPlayerDataSource
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.room.RoomPlayer
import ru.storytellers.model.entity.room.db.AppDatabase

class PlayerDataSource(
    private val database: AppDatabase,
    private val characterDataSource: ICharacterDataSource
) : IPlayerDataSource {

    override fun insertOrReplace(storyId: Long, player: Player): @NonNull Completable =
        Completable.fromAction {
            val roomPlayer = RoomPlayer(
                player.id,
                player.name,
                player.character?.id ?: 0,
                storyId
            )
            database.playerDao.insert(roomPlayer)
        }

    override fun getPlayerById(playerId: Long): Single<Player> =
        Single.create { emitter ->
            database.playerDao.getPlayerById(playerId)?.let { roomPlayer ->
                characterDataSource.getCharacterById(roomPlayer.characterId)
                    .flatMap { character ->
                        return@flatMap Single.fromCallable {
                            emitter.onSuccess(
                                Player(
                                    roomPlayer.id,
                                    roomPlayer.name,
                                    character
                                )
                            )
                        }
                    } ?: let {
                    emitter.onError(RuntimeException("No such character in database"))
                }
            }
        }

    override fun getAll(): Single<List<Player>> =
        Single.create { emitter ->
            characterDataSource.getAll().flatMap { characterList ->
                database.playerDao.getAll()?.let { playerList ->
                    val filledList = playerList.map { roomPlayer ->
                        val character =
                            characterList.find { it.id == roomPlayer.characterId }!!
                        Player(
                            roomPlayer.id,
                            roomPlayer.name,
                            character
                        )
                    }
                    Single.fromCallable { emitter.onSuccess(filledList) }
                } ?: let {
                    Single.fromCallable { emitter.onError(RuntimeException("No players in database")) }
                }
            }
        }

}