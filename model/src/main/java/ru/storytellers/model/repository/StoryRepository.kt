package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.storytellers.model.datasource.IStoryDataSource
import ru.storytellers.model.entity.Story

class StoryRepository(private val localDataSource: IStoryDataSource): IStoryRepository {
    override fun insertOrReplace(story: Story): @NonNull Completable =
        localDataSource.insertOrReplace(story)
            .subscribeOn(Schedulers.io())

    override fun deleteStoryById(storyId: Long): Single<Int> =
        localDataSource.deleteStoryById(storyId)
            .subscribeOn(Schedulers.io())

    override fun getStoryById(storyId: Long): Single<Story> =
        localDataSource.getStoryById(storyId)
            .subscribeOn(Schedulers.io())

    override fun getStoryWithSentencesById(storyId: Long): Single<Story> =
        localDataSource.getStoryWithSentencesById(storyId)
            .subscribeOn(Schedulers.io())

    override fun getAll(): Single<List<Story>> =
        localDataSource.getAll()
            .subscribeOn(Schedulers.io())

}