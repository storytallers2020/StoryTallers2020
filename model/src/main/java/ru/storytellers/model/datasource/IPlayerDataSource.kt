package ru.storytellers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Player

interface IPlayerDataSource {
    fun insertOrReplace(storyId: Long, player: Player): @NonNull Completable
    fun getPlayerById(playerId: Long): Single<Player>
    fun getAll(): Single<List<Player>>
}