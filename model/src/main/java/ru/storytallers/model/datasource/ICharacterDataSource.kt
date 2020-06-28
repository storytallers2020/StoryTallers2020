package ru.storytallers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytallers.model.entity.Character

interface ICharacterDataSource {
    fun insertOrReplace(character: Character): @NonNull Completable
    fun getCharacterById(characterId: Long): Single<Character>
    fun getAll(): Single<List<Character>>
}