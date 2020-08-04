package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.GameStorage
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.assistant.TitleAndSaveModelAssistant
import ru.storytellers.utils.getUid
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import java.lang.StringBuilder


class TitleAndSaveStoryViewModel(
    private val assistantModel:TitleAndSaveModelAssistant
): BaseViewModel<DataModel>() {
    private var titleStory:String?=null
    private val coverLiveDate= MutableLiveData<Cover>()
    private val  successSaveFlagLiveDate= MutableLiveData<Boolean>()

    fun setTitleStory(title:String){
        titleStory= title
        StoryTallerApp.instance.gameStorage.setTitleStory(titleStory!!)
    }

    fun subscribeOnSuccessSaveFlag()=successSaveFlagLiveDate

    fun subscribeOnCover():LiveData<Cover>{
        coverLiveDate.value=StoryTallerApp.instance.gameStorage.getCoverStoryTaller()
        return coverLiveDate
    }
    fun createStoryTaller() {
        val gameStorage=StoryTallerApp.instance.gameStorage
        val story= Story(
            getUid(),
            getTitle(gameStorage),
            getAuthors(gameStorage),
            gameStorage.getCoverStoryTaller()?.imageUrl!!,
            gameStorage.getLocationGame(),
            getListSentenceOfTale(gameStorage)
        )
        saveStory(story)
    }

    private fun getTitle(gameStorage: GameStorage): String {
        var title: String = "no title"
        gameStorage.getTitleStory()?.let { title = it }
        return title
    }

    private fun getAuthors(gmStorage:GameStorage):String{
       val namePlayers= StringBuilder()
       val listPlayer=gmStorage.getListPlayers()
       listPlayer.forEach {
               namePlayers.append(it.name).append(" ")
       }
       return namePlayers.toString()
   }
    private fun getListSentenceOfTale(gmStorage:GameStorage):List<SentenceOfTale>{
        val list= mutableListOf<SentenceOfTale>()
        gmStorage.getListSentenceOfTale().forEach { it?.let { list.add(it) } }
        return list.toList()
    }
//    private fun saveStory(story: Story){
//        successSaveFlagLiveDate.value=assistantModel.saveStory(story)
//    }

    private fun saveStory(story: Story){
        assistantModel.saveStory(story)
            .subscribe({
                successSaveFlagLiveDate.value = true
            },{
                successSaveFlagLiveDate.value = false
            })
    }

}