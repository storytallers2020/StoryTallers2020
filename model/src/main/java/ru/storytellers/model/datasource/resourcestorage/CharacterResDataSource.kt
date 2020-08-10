package ru.storytellers.model.datasource.resourcestorage

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.resourcestorage.storage.CharacterStorage
import ru.storytellers.model.entity.Character

class CharacterResDataSource(private val characterStorage: CharacterStorage) :
    ICharacterDataSource {

    override fun insertOrReplace(character: Character): Completable =
        Completable.fromAction {
            characterStorage.insertOrReplace(character)
        }

    override fun getCharacterById(characterId: Long): Single<Character> =
        Single.create { emitter ->
            characterStorage.getCharacterById(characterId)?.let {
                emitter.onSuccess(it)
            } ?: let {
                emitter.onError(RuntimeException("No such character in database"))
            }
        }

    override fun getAll(): Single<List<Character>> =
        Single.fromCallable { characterStorage.getAll() }

}