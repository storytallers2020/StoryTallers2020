package ru.storytellers.model.datasource.resourcestorage

import android.content.Context
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.resources.R
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.utils.resourceToString
import ru.storytellers.model.entity.*

class LocationResDataSource(context: Context) : ILocationDataSource {

    //region locationsList...
    private val locationList: MutableList<Location> = mutableListOf(
        Location(
            1,
            context.getString(R.string.location_kingdom1_name),
            resourceToString(context, R.drawable.location_castle_fade),
            resourceToString(context, R.drawable.location_castle_small),
            "В далеком-далеком королевстве..."
        ),
        Location(
            2,
            context.getString(R.string.location_space1_name),
            resourceToString(context, R.drawable.location_cosmos_fade),
            resourceToString(context, R.drawable.location_cosmos_small),
            "В далеком-далеком космосе..."
        )
    )
    //endregion

    override fun insertOrReplace(location: Location): Completable =

        Completable.fromAction {
            locationList.find { it.id == location.id }?.let { foundLocation ->
                locationList.remove(foundLocation)
            }
            locationList.add(location)
        }

    override fun getLocationById(locationId: Long): Single<Location> =
        Single.create { emitter ->
            locationList.find { location ->
                location.id == locationId
            }?.let {
                emitter.onSuccess(it)
            } ?: let {
                emitter.onError(RuntimeException("No such character in database"))
            }
        }

    override fun getAll(): Single<List<Location>> =
        Single.create { emitter ->
            emitter.onSuccess(locationList)
        }

}