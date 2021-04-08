package ru.storytellers.model.datasource.remote

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.jetbrains.annotations.NotNull
import retrofit2.Response
import retrofit2.http.*
import ru.storytellers.model.datasource.remote.api.*
import ru.storytellers.model.entity.User
import ru.storytellers.model.entity.UserAccount

interface IRemoteDataSource {
    @GET("locations/getOnlyActive")
    fun getLocations(): Single<LocationListApi>

    @GET("characters/getOnlyActive")
    fun getCharacters(): Single<CharactersListApi>

    @GET("covers/getOnlyActive")
    fun getCovers(): Single<CoverListApi>

    @POST("v1/full-data/")
    fun saveUser(@Body user : UserApi) : Completable

    @GET("v1/getUserId/")
    fun getUserById(@Query("userId") userId : Long ) : Single<UserApi>

    @GET("v1/getUserId/")
    fun getAllUser() : Single<List<UserAccountApi>>
}