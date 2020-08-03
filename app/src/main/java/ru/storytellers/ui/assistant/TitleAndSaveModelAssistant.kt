package ru.storytellers.ui.assistant

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.storytellers.model.entity.Story
import ru.storytellers.model.repository.IStoryRepository

//класс для работы с базой данный и сервером
class TitleAndSaveModelAssistant(
    private val storyRepository: IStoryRepository
) {

//    fun saveStory(story: Story):Boolean{
//        var successSaveFlag:Boolean=false
//        storyRepository.insertOrReplace(story)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                successSaveFlag=true
//            },{
//                successSaveFlag=false
//            })
//        return successSaveFlag
//    }

    fun saveStory(story: Story): Completable =
        storyRepository.insertOrReplace(story)
            .observeOn(AndroidSchedulers.mainThread())

}