package ru.storytellers.model.datasource

import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Character

interface ICharacterDataSource {
    fun insertOrReplace(character: Character)
    fun getLocationById(characterId: Long): Single<Character>
    fun getAll(): Single<List<Character>>
}