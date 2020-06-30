package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Story

interface IStoryRepository {
    fun insertOrReplace(story: Story): @NonNull Completable
    fun getStoryById(storyId: Long): Single<Story>
    fun getAll(): Single<List<Story>>
}
