package ru.storytellers.model.datasource.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.storytellers.model.datasource.remote.api.*

interface IRemoteDataSource {
    @GET("locations/getOnlyActive")
    fun getLocations(): Single<LocationListApi>

    @GET("characters/getOnlyActive")
    fun getCharacters(): Single<CharactersListApi>

    @GET("covers/getOnlyActive")
    fun getCovers(): Single<CoverListApi>

    @GET("words/getAll")
    fun getWords(@Query("lang") lang: String): Single<WordListApi>


    @GET("versions/getAll")
    fun getVersions(): Single<VersionsApiList>

}