package ru.storytallers.model.datasource

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytallers.model.entity.Story


interface IStoryDataSource {
    fun insertOrReplace(story: Story): @NonNull Completable
    fun getStoryById(storyId: Long): Single<Story>
    fun getAll(): Single<List<Story>>
}