package ru.storytellers.model.datasource.resourcestorage.storage

import android.content.Context
import ru.storytellers.model.entity.Location
import ru.storytellers.resources.R
import ru.storytellers.utils.resourceToString

class LocationStorage(context: Context)  {

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

    fun insertOrReplace(location: Location) {
        locationList.find { it.id == location.id }?.let { foundLocation ->
            locationList.remove(foundLocation)
        }
        locationList.add(location)
    }

    fun getLocationById(locationId: Long): Location? =
        locationList.find { it.id == locationId }

    fun getAll(): List<Location> = locationList

}