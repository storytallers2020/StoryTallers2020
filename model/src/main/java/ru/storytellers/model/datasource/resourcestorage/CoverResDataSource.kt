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