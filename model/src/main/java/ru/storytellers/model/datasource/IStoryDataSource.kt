package ru.storytellers.model.datasource

import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Story


interface IStoryDataSource {
    fun insertOrReplace(story: Story)
    fun getStoryById(storyId: Long): Single<Story>
    fun getAll(): Single<List<Story>>
}