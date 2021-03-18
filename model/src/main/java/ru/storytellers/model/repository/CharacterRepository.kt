package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.cache.ICashImageDataSource
import ru.storytellers.model.cache.IImageCache
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.entity.Character
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.utils.toAvatarList

class CharacterRepository(
    private val localDataSource: ICharacterDataSource,
) : ICharacterRepository {

    override fun insertOrReplace(character: Character): Completable =
        localDataSource.insertOrReplace(character)
            .subscribeOn(Schedulers.io())

    override fun getCharacterById(characterId: Long): Single<Character> =
        localDataSource.getCharacterById(characterId)
            .subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Character>> =
        localDataSource.getAll()
            .subscribeOn(Schedulers.io())
}
