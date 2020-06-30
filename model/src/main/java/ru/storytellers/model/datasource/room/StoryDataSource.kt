package ru.storytellers.model.datasource.room

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.datasource.IStoryDataSource
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Story
import ru.storytellers.model.entity.room.RoomStory
import ru.storytellers.model.entity.room.db.AppDatabase

class StoryDataSource(
    private val database: AppDatabase,
    private val locationDataSource: LocationDataSource
) : IStoryDataSource {
    override fun insertOrReplace(story: Story): @NonNull Completable =
        Completable.fromAction {
            val roomStory = RoomStory(
                story.id,
                story.name,
                story.authors,
                story.coverUrl,
                story.location?.id ?: 0
            )
            database.storyDao.insert(roomStory)
        }

    override fun getStoryById(storyId: Long): Single<Story> =

        Single.create { emitter ->
            database.storyDao.getStoryById(storyId)?.let { roomStory ->
                locationDataSource.getLocationById(roomStory.locationId).flatMap {
                    return@flatMap Single.fromCallable {
                        emitter.onSuccess(
                            Story(
                                roomStory.id,
                                roomStory.name,
                                roomStory.authors,
                                roomStory.coverUrl,
                                it
                            )
                        )
                    }
                }
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }

    override fun getAll(): Single<List<Story>> =
        Single.create { emitter ->
            database.storyDao.getAll()?.let { roomStoryList ->
                val storyList = roomStoryList.map { roomStory ->
                    val location = database.locationDao.getLocationById(roomStory.locationId)?.let {
                        Location(it.id, it.name, it.description, it.imageUrl)
                    }
                    Story(
                        roomStory.id,
                        roomStory.name,
                        roomStory.authors,
                        roomStory.coverUrl,
                        location
                    )
                }
                emitter.onSuccess(storyList)
            } ?: let {
                emitter.onError(RuntimeException("No such story in database"))
            }
        }
}