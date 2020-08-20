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
            context.getString(R.string.cover1_name),
            resourceToString(context, R.drawable.cover_1),
            resourceToString(context, R.drawable.cover_1_small)
        ),
        Cover(
            2,
            context.getString(R.string.cover2_name),
            resourceToString(context, R.drawable.cover_2),
            resourceToString(context, R.drawable.cover_2_small)
        ),
        Cover(
            3,
            context.getString(R.string.cover3_name),
            resourceToString(context, R.drawable.cover_3),
            resourceToString(context, R.drawable.cover_3_small)
        ),
        Cover(
            4,
            context.getString(R.string.cover4_name),
            resourceToString(context, R.drawable.cover_4),
            resourceToString(context, R.drawable.cover_4_small)
        ),
        Cover(
            5,
            context.getString(R.string.cover5_name),
            resourceToString(context, R.drawable.cover_5),
            resourceToString(context, R.drawable.cover_5_small)
        ),
        Cover(
            6,
            context.getString(R.string.location_amusement_park_name),
            resourceToString(context, R.drawable.cover_amusementpark),
            resourceToString(context, R.drawable.cover_amusementpark)
        ),
        Cover(
            7,
            context.getString(R.string.location_cave_of_wonders_name),
            resourceToString(context, R.drawable.cover_caveofwonders),
            resourceToString(context, R.drawable.cover_caveofwonders)
        ),
        Cover(
            8,
            context.getString(R.string.location_chamber_of_secrets_name),
            resourceToString(context, R.drawable.cover_chamberofsecrets),
            resourceToString(context, R.drawable.cover_chamberofsecrets)
        ),
        Cover(
            9,
            context.getString(R.string.location_city_name),
            resourceToString(context, R.drawable.cover_city),
            resourceToString(context, R.drawable.cover_city)
        ),
        Cover(
            10,
            context.getString(R.string.location_dwarf_house_name),
            resourceToString(context, R.drawable.cover_dwarf_house),
            resourceToString(context, R.drawable.cover_dwarf_house)
        ),
        Cover(
            11,
            context.getString(R.string.location_halloween_name),
            resourceToString(context, R.drawable.cover_halloween),
            resourceToString(context, R.drawable.cover_halloween)
        ),
        Cover(
            12,
            context.getString(R.string.location_house_of_magic_name),
            resourceToString(context, R.drawable.cover_house_of_magic),
            resourceToString(context, R.drawable.cover_house_of_magic)
        ),
        Cover(
            13,
            context.getString(R.string.location_kingdom_name),
            resourceToString(context, R.drawable.cover_kingdom),
            resourceToString(context, R.drawable.cover_kingdom)
        ),
        Cover(
            14,
            context.getString(R.string.location_lighthouse_name),
            resourceToString(context, R.drawable.cover_lighthouse),
            resourceToString(context, R.drawable.cover_lighthouse)
        ),
        Cover(
            15,
            context.getString(R.string.location_night_city_name),
            resourceToString(context, R.drawable.cover_night_city),
            resourceToString(context, R.drawable.cover_night_city)
        ),
        Cover(
            16,
            context.getString(R.string.location_paper_name),
            resourceToString(context, R.drawable.cover_paper),
            resourceToString(context, R.drawable.cover_paper)
        ),
        Cover(
            17,
            context.getString(R.string.location_pirate_ship_name),
            resourceToString(context, R.drawable.cover_pirate_ship),
            resourceToString(context, R.drawable.cover_pirate_ship)
        ),
        Cover(
            18,
            context.getString(R.string.location_playground_name),
            resourceToString(context, R.drawable.cover_playground),
            resourceToString(context, R.drawable.cover_playground)
        ),
        Cover(
            19,
            context.getString(R.string.location_skeletons_name),
            resourceToString(context, R.drawable.cover_skeletons),
            resourceToString(context, R.drawable.cover_skeletons)
        ),
        Cover(
            20,
            context.getString(R.string.location_space_name),
            resourceToString(context, R.drawable.cover_space),
            resourceToString(context, R.drawable.cover_space)
        ),
        Cover(
            21,
            context.getString(R.string.location_spaceship_name),
            resourceToString(context, R.drawable.cover_spaceship),
            resourceToString(context, R.drawable.cover_spaceship)
        ),
        Cover(
            22,
            context.getString(R.string.location_tree_house_name),
            resourceToString(context, R.drawable.cover_tree_house),
            resourceToString(context, R.drawable.cover_tree_house)
        ),
        Cover(
            23,
            context.getString(R.string.location_waterfall_name),
            resourceToString(context, R.drawable.cover_waterfall),
            resourceToString(context, R.drawable.cover_waterfall)
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