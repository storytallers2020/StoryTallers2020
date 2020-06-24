package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.room.RoomCharacter
import ru.storytellers.model.entity.room.db.AppDatabase

class CharacterDataSource(private val database: AppDatabase): ICharacterDataSource {
    override fun insertOrReplace(character: Character) {
        val roomCharacter = RoomCharacter(
            character.id,
            character.name,
            character.avatarUrl
        )
        database.characterDao.insert(roomCharacter)
    }

    override fun getLocationById(characterId: Long): Single<Character> =
        Single.create { emitter ->
            database.characterDao.getCharacterById(characterId)?.let { roomCharacter ->
                emitter.onSuccess(
                    Character(
                        roomCharacter.id,
                        roomCharacter.name,
                        roomCharacter.avatarUrl
                    )
                )
            } ?: let {
                emitter.onError(RuntimeException("No such character in database"))
            }
        }

    override fun getAll(): Single<List<Character>> =
        Single.create { emitter ->
            database.characterDao.getAll()?.let { roomCharacterList ->
                val characterList = roomCharacterList.map {
                    Character(
                        it.id,
                        it.name,
                        it.avatarUrl
                    )
                }
                emitter.onSuccess(characterList)
            } ?: let {
                emitter.onError(RuntimeException("No such character in database"))
            }
        }
}