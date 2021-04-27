package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.utils.toCharacter
import ru.storytellers.utils.toCharacterList
import ru.storytellers.utils.toRoomCharacter
import ru.storytellers.utils.toRoomCharacterList

class CharacterDataSource(private val database: AppDatabase) : ICharacterDataSource {

    override fun insertOrReplace(character: Character): @NonNull Completable =
        Completable.fromAction {
            database.characterDao.insert(character.toRoomCharacter())
        }

    override fun insertOrReplace(characterList: List<Character>): Completable  =
        Completable.fromAction {
            database.characterDao.insert(characterList.toRoomCharacterList())
        }

    override fun getCharacterById(characterId: Long): Single<Character> =
        Single.create { emitter ->
            database.characterDao.getCharacterById(characterId)?.let { roomCharacter ->
                emitter.onSuccess(roomCharacter.toCharacter())
            } ?: let {
                emitter.onError(RuntimeException("No such character in database"))
            }
        }

    override fun getAll(): Single<List<Character>> =
        Single.create { emitter ->
            database.characterDao.getAll()?.let { roomCharacterList ->
                emitter.onSuccess(roomCharacterList.toCharacterList())
            } ?: let {
                emitter.onError(RuntimeException("No such character in database"))
            }
        }
}