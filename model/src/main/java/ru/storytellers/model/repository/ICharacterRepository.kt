package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Character

interface ICharacterRepository {
    fun insertOrReplace(character: Character): @NonNull Completable
    fun getCharacterById(characterId: Long): Single<Character>
    fun getAll(): Single<List<Character>>
}
