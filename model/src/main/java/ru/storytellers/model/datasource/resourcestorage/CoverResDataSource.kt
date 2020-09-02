package ru.storytellers.model.datasource.resourcestorage

import android.content.Context
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.ICoverDataSource
import ru.storytellers.model.entity.Cover
import ru.storytellers.resources.R
import ru.storytellers.utils.resourceToString

class CoverResDataSource(context: Context) : ICoverDataSource {

    //region coverList...
    private val coverList: MutableList<Cover> = mutableListOf(

        Cover(
            1,
            context.getString(R.string.location_amusement_park_name),
            resourceToString(context, R.drawable.cover_amusement_park),
            resourceToString(context, R.drawable.cover_amusement_park)
        ),
        Cover(
            2,
            context.getString(R.string.location_beach_name),
            resourceToString(context, R.drawable.cover_beach),
            resourceToString(context, R.drawable.cover_beach)
        ),
        Cover(
            3,
            context.getString(R.string.location_blue_name),
            resourceToString(context, R.drawable.cover_blue),
            resourceToString(context, R.drawable.cover_blue)
        ),
        Cover(
            4,
            context.getString(R.string.location_camp),
            resourceToString(context, R.drawable.cover_camp),
            resourceToString(context, R.drawable.cover_camp)
        ),
        Cover(
            5,
            context.getString(R.string.location_castle_name),
            resourceToString(context, R.drawable.cover_castle),
            resourceToString(context, R.drawable.cover_castle)
        ),
        Cover(
            6,
            context.getString(R.string.location_cave_of_wonders_name),
            resourceToString(context, R.drawable.cover_cave_of_wonders),
            resourceToString(context, R.drawable.cover_cave_of_wonders)
        ),
        Cover(
            7,
            context.getString(R.string.location_chamber_of_secrets_name),
            resourceToString(context, R.drawable.cover_chamber_of_secrets),
            resourceToString(context, R.drawable.cover_chamber_of_secrets)
        ),
        Cover(
            8,
            context.getString(R.string.location_city_name),
            resourceToString(context, R.drawable.cover_city),
            resourceToString(context, R.drawable.cover_city)
        ),
        Cover(
            9,
            context.getString(R.string.location_dwarf_house_name),
            resourceToString(context, R.drawable.cover_dwarf_house),
            resourceToString(context, R.drawable.cover_dwarf_house)
        ),
        Cover(
            10,
            context.getString(R.string.location_forest_name),
            resourceToString(context, R.drawable.cover_forest),
            resourceToString(context, R.drawable.cover_forest)
        ),
        Cover(
            11,
            context.getString(R.string.location_garden_name),
            resourceToString(context, R.drawable.cover_garden),
            resourceToString(context, R.drawable.cover_garden)
        ),
        Cover(
            12,
            context.getString(R.string.location_halloween_name),
            resourceToString(context, R.drawable.cover_halloween),
            resourceToString(context, R.drawable.cover_halloween)
        ),
        Cover(
            13,
            context.getString(R.string.location_house_of_magic_name),
            resourceToString(context, R.drawable.cover_house_of_magic),
            resourceToString(context, R.drawable.cover_house_of_magic)
        ),
        Cover(
            14,
            context.getString(R.string.location_kingdom_name),
            resourceToString(context, R.drawable.cover_kingdom),
            resourceToString(context, R.drawable.cover_kingdom)
        ),
        Cover(
            15,
            context.getString(R.string.location_lighthouse_name),
            resourceToString(context, R.drawable.cover_lighthouse),
            resourceToString(context, R.drawable.cover_lighthouse)
        ),
        Cover(
            16,
            context.getString(R.string.location_magic_oak_name),
            resourceToString(context, R.drawable.cover_magic_oak),
            resourceToString(context, R.drawable.cover_magic_oak)
        ),
        Cover(
            17,
            context.getString(R.string.location_night_city_name),
            resourceToString(context, R.drawable.cover_night_city),
            resourceToString(context, R.drawable.cover_night_city)
        ),
        Cover(
            18,
            context.getString(R.string.location_paper_name),
            resourceToString(context, R.drawable.cover_paper),
            resourceToString(context, R.drawable.cover_paper)
        ),
        Cover(
            19,
            context.getString(R.string.location_pirate_ship_name),
            resourceToString(context, R.drawable.cover_pirate_ship),
            resourceToString(context, R.drawable.cover_pirate_ship)
        ),
        Cover(
            20,
            context.getString(R.string.location_planets_name),
            resourceToString(context, R.drawable.cover_planets),
            resourceToString(context, R.drawable.cover_planets)
        ),
        Cover(
            21,
            context.getString(R.string.location_playground_name),
            resourceToString(context, R.drawable.cover_playground),
            resourceToString(context, R.drawable.cover_playground)
        ),
        Cover(
            22,
            context.getString(R.string.location_roses_name),
            resourceToString(context, R.drawable.cover_roses),
            resourceToString(context, R.drawable.cover_roses)
        ),
        Cover(
            23,
            context.getString(R.string.location_sea_world_name),
            resourceToString(context, R.drawable.cover_seaworld),
            resourceToString(context, R.drawable.cover_seaworld)
        ),
        Cover(
            24,
            context.getString(R.string.location_skeletons_name),
            resourceToString(context, R.drawable.cover_skeletons),
            resourceToString(context, R.drawable.cover_skeletons)
        ),
        Cover(
            25,
            context.getString(R.string.location_space_name),
            resourceToString(context, R.drawable.cover_space),
            resourceToString(context, R.drawable.cover_space)
        ),
        Cover(
            26,
            context.getString(R.string.location_spaceship_name),
            resourceToString(context, R.drawable.cover_spaceship),
            resourceToString(context, R.drawable.cover_spaceship)
        ),
        Cover(
            27,
            context.getString(R.string.location_swamp_name),
            resourceToString(context, R.drawable.cover_swamp),
            resourceToString(context, R.drawable.cover_swamp)
        ),
        Cover(
            28,
            context.getString(R.string.location_swing_name),
            resourceToString(context, R.drawable.cover_swing),
            resourceToString(context, R.drawable.cover_swing)
        ),
        Cover(
            29,
            context.getString(R.string.location_tree_house_name),
            resourceToString(context, R.drawable.cover_tree_house),
            resourceToString(context, R.drawable.cover_tree_house)
        ),
        Cover(
            30,
            context.getString(R.string.location_waterfall_name),
            resourceToString(context, R.drawable.cover_waterfall),
            resourceToString(context, R.drawable.cover_waterfall)
        ),
        Cover(
            31,
            context.getString(R.string.location_witch_room_name),
            resourceToString(context, R.drawable.cover_witch_room),
            resourceToString(context, R.drawable.cover_witch_room)
        ),
        Cover(
            32,
            context.getString(R.string.location_zombie_name),
            resourceToString(context, R.drawable.cover_zombie),
            resourceToString(context, R.drawable.cover_zombie)
        )
    )
    //endregion

    override fun insertOrReplace(cover: Cover): Completable =

        Completable.fromAction {
            coverList.find { it.id == cover.id }?.let { foundCover ->
                coverList.remove(foundCover)
            }
            coverList.add(cover)
        }

    override fun getCoverById(coverId: Long): Single<Cover> =
        Single.create { emitter ->
            coverList.find { cover ->
                cover.id == coverId
            }?.let {
                emitter.onSuccess(it)
            } ?: let {
                emitter.onError(RuntimeException("No such cover in database"))
            }
        }

    override fun getAll(): Single<List<Cover>> =
        Single.create { emitter ->
            emitter.onSuccess(coverList)
        }

}