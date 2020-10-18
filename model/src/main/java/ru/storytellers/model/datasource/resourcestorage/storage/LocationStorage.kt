package ru.storytellers.model.datasource.resourcestorage.storage

import android.content.Context
import ru.storytellers.model.entity.Location
import ru.storytellers.resources.R
import ru.storytellers.utils.resourceToString

class LocationStorage(context: Context)  {

    private val locationList: MutableList<Location> = mutableListOf(
        Location(
            1,
            context.getString(R.string.location_amusement_park_name),
            resourceToString(context, R.drawable.location_amusementpark),
            resourceToString(context, R.drawable.location_amusementpark_small),
            ""
        ),
        Location(
            2,
            context.getString(R.string.location_beach_name),
            resourceToString(context, R.drawable.location_beach),
            resourceToString(context, R.drawable.location_beach_small),
            ""
        ),
//        Location(
//            3,
//            context.getString(R.string.location_camp),
//            resourceToString(context, R.drawable.location_camp),
//            resourceToString(context, R.drawable.location_camp_small),
//            ""
//        ),
//        Location(
//            4,
//            context.getString(R.string.location_castle_name),
//            resourceToString(context, R.drawable.location_castle),
//            resourceToString(context, R.drawable.location_castle_small),
//            ""
//        ),
        Location(
            5,
            context.getString(R.string.location_cave_of_wonders_name),
            resourceToString(context, R.drawable.location_caveofwonders),
            resourceToString(context, R.drawable.location_caveofwonders_small),
            ""
        ),
//        Location(
//            6,
//            context.getString(R.string.location_chamber_of_secrets_name),
//            resourceToString(context, R.drawable.location_chamberofsecrets),
//            resourceToString(context, R.drawable.location_chamberofsecrets_small),
//            ""
//        ),
//        Location(
//            7,
//            context.getString(R.string.location_city_name),
//            resourceToString(context, R.drawable.location_city),
//            resourceToString(context, R.drawable.location_city_small),
//            ""
//        ),
        Location(
            8,
            context.getString(R.string.location_dwarf_house_name),
            resourceToString(context, R.drawable.location_dwarf_house),
            resourceToString(context, R.drawable.location_dwarf_house_small),
            ""
        ),
        Location(
            9,
            context.getString(R.string.location_forest_name),
            resourceToString(context, R.drawable.location_forest),
            resourceToString(context, R.drawable.location_forest_small),
            ""
        ),
//        Location(
//            10,
//            context.getString(R.string.location_garden_name),
//            resourceToString(context, R.drawable.location_garden),
//            resourceToString(context, R.drawable.location_garden_small),
//            ""
//        ),
//        Location(
//            11,
//            context.getString(R.string.location_halloween_name),
//            resourceToString(context, R.drawable.location_halloween),
//            resourceToString(context, R.drawable.location_halloween_small),
//            ""
//        ),
//        Location(
//            12,
//            context.getString(R.string.location_house_of_magic_name),
//            resourceToString(context, R.drawable.location_house_of_magic),
//            resourceToString(context, R.drawable.location_house_of_magic_small),
//            ""
//        ),
        Location(
            13,
            context.getString(R.string.location_kingdom_name),
            resourceToString(context, R.drawable.location_kingdom),
            resourceToString(context, R.drawable.location_kingdom_small),
            ""
        ),
//        Location(
//            14,
//            context.getString(R.string.location_lighthouse_name),
//            resourceToString(context, R.drawable.location_lighthouse),
//            resourceToString(context, R.drawable.location_lighthouse_small),
//            ""
//        ),
//        Location(
//            15,
//            context.getString(R.string.location_magic_oak_name),
//            resourceToString(context, R.drawable.location_magic_oak),
//            resourceToString(context, R.drawable.location_magic_oak_small),
//            ""
//        ),
//        Location(
//            16,
//            context.getString(R.string.location_night_city_name),
//            resourceToString(context, R.drawable.location_night_city),
//            resourceToString(context, R.drawable.location_night_city_small),
//            ""
//        ),
//        Location(
//            17,
//            context.getString(R.string.location_paper_name),
//            resourceToString(context, R.drawable.location_paper),
//            resourceToString(context, R.drawable.location_paper_small),
//            ""
//        ),
//        Location(
//            18,
//            context.getString(R.string.location_pirate_ship_name),
//            resourceToString(context, R.drawable.location_pirate_ship),
//            resourceToString(context, R.drawable.location_pirate_ship_small),
//            ""
//        ),
//        Location(
//            19,
//            context.getString(R.string.location_playground_name),
//            resourceToString(context, R.drawable.location_playground),
//            resourceToString(context, R.drawable.location_playground_small),
//            ""
//        ),
//        Location(
//            20,
//            context.getString(R.string.location_sea_world_name),
//            resourceToString(context, R.drawable.location_sea_world),
//            resourceToString(context, R.drawable.location_sea_world_small),
//            ""
//        ),
        Location(
            21,
            context.getString(R.string.location_skeletons_name),
            resourceToString(context, R.drawable.location_skeletons),
            resourceToString(context, R.drawable.location_skeletons_small),
            ""
        ),
//        Location(
//            22,
//            context.getString(R.string.location_space_name),
//            resourceToString(context, R.drawable.location_space),
//            resourceToString(context, R.drawable.location_space_small),
//            ""
//        ),
//        Location(
//            23,
//            context.getString(R.string.location_spaceship_name),
//            resourceToString(context, R.drawable.location_spaceship),
//            resourceToString(context, R.drawable.location_spaceship_small),
//            ""
//        ),
//        Location(
//            24,
//            context.getString(R.string.location_swing_name),
//            resourceToString(context, R.drawable.location_swing),
//            resourceToString(context, R.drawable.location_swing_small),
//            ""
//        ),
//        Location(
//            25,
//            context.getString(R.string.location_tree_house_name),
//            resourceToString(context, R.drawable.location_tree_house),
//            resourceToString(context, R.drawable.location_tree_house_small),
//            ""
//        ),
//        Location(
//            26,
//            context.getString(R.string.location_waterfall_name),
//            resourceToString(context, R.drawable.location_waterfall),
//            resourceToString(context, R.drawable.location_waterfall_small),
//            ""
//        ),
        Location(
            27,
            context.getString(R.string.location_witch_room_name),
            resourceToString(context, R.drawable.location_witch_room),
            resourceToString(context, R.drawable.location_witch_room_small),
            ""
        )
//        ,
//        Location(
//            28,
//            context.getString(R.string.location_zombie_name),
//            resourceToString(context, R.drawable.location_zombie),
//            resourceToString(context, R.drawable.location_zombie_small),
//            ""
//        )
    )

    fun insertOrReplace(location: Location) {
        locationList.find { it.id == location.id }?.let { foundLocation ->
            locationList.remove(foundLocation)
        }
        locationList.add(location)
    }

    fun insertOrReplace(list: List<Location>) {
        list.map {location ->
            locationList.find { it.id == location.id }?.let { foundLocation ->
                locationList.remove(foundLocation)
            }
            locationList.add(location)
        }
    }

    fun getLocationById(locationId: Long): Location? =
        locationList.find { it.id == locationId }

    fun getAll(): List<Location> = locationList

}