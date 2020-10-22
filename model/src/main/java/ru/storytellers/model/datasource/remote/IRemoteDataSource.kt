package ru.storytellers.model.datasource.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import ru.storytellers.model.datasource.remote.api.CharactersListApi
import ru.storytellers.model.datasource.remote.api.LocationListApi

interface IRemoteDataSource {
    @GET("locations/getAll")
    fun getLocations(): Single<LocationListApi>

    @GET("characters/getAll")
    fun getCharacters(): Single<CharactersListApi>

}