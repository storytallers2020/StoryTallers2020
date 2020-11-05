package ru.storytellers.model.datasource.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import ru.storytellers.model.datasource.remote.api.CharactersListApi
import ru.storytellers.model.datasource.remote.api.CoverListApi
import ru.storytellers.model.datasource.remote.api.LocationListApi

interface IRemoteDataSource {
    @GET("locations/getOnlyActive")
    fun getLocations(): Single<LocationListApi>

    @GET("characters/getOnlyActive")
    fun getCharacters(): Single<CharactersListApi>

    @GET("covers/getOnlyActive")
    fun getCovers(): Single<CoverListApi>

}