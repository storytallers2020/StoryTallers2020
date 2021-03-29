package ru.storytellers.model.datasource.remote

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.jetbrains.annotations.NotNull
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.storytellers.model.datasource.remote.api.CharactersListApi
import ru.storytellers.model.datasource.remote.api.CoverListApi
import ru.storytellers.model.datasource.remote.api.LocationListApi
import ru.storytellers.model.datasource.remote.api.UserAccountApi
import ru.storytellers.model.entity.UserAccount

interface IRemoteDataSource {
    @GET("locations/getOnlyActive")
    fun getLocations(): Single<LocationListApi>

    @GET("characters/getOnlyActive")
    fun getCharacters(): Single<CharactersListApi>

    @GET("covers/getOnlyActive")
    fun getCovers(): Single<CoverListApi>

    @POST("v1/full-data/")
    fun saveUser(@Body userAccount : UserAccountApi) : Completable
}