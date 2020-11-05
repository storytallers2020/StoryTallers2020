package ru.storytellers.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Story

interface IStoryRepository {
    fun insertOrReplace(story: Story): @NonNull Completable
    fun deleteStoryById(storyId: Long): Single<Int>
    fun getStoryById(storyId: Long): Single<Story>
    fun getStoryWithSentencesById(storyId: Long): Single<Story>
    fun getAll(): Single<List<Story>>
    fun updateTitleStory(titleStory: String, storyId: Long):Single<Int>
}
