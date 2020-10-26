package ru.storytellers.model.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Location
import ru.storytellers.model.network.INetworkStatus

class CharacterRepository(
    private val networkStatus: INetworkStatus,
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ICharacterDataSource
): ICharacterRepository {

    override fun insertOrReplace(character: Character) =
        localDataSource.insertOrReplace(character)
            .subscribeOn(Schedulers.io())

    override fun getCharacterById(characterId: Long): Single<Character> =
        localDataSource.getCharacterById(characterId)
            .subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Character>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                remoteDataSource.getCharacters().flatMap { characterListApi ->
                    localDataSource
                        .insertOrReplace(characterListApi.characters)
                        .toSingleDefault(characterListApi.characters)
                }
            } else {
                localDataSource.getAll()
            }
        }.subscribeOn(Schedulers.io())
}
