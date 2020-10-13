package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.engine.GameStorage
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.assistant.TitleAndSaveModelAssistant
import ru.storytellers.utils.*
import ru.storytellers.utils.StatHelper.Companion.getNumberSymbolsInStory
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel


class TitleAndSaveStoryViewModel(
    private val assistantModel: TitleAndSaveModelAssistant
) : BaseViewModel<DataModel>() {
    private val coverLiveDate = MutableLiveData<Cover>()
    private val successSaveFlagLiveDate = MutableLiveData<Boolean>()

    fun setTitleStory(title: String) {
        StoryHeroesApp.instance.gameStorage.setTitleStory(title)
    }

    fun subscribeOnSuccessSaveFlag() = successSaveFlagLiveDate

    fun subscribeOnCover(): LiveData<Cover> {
        coverLiveDate.value = StoryHeroesApp.instance.gameStorage.getCoverStory()
        return coverLiveDate
    }

    fun saveStory(): Boolean {
        with(StoryHeroesApp.instance.gameStorage) {
            return if (getTitle(this).isEmpty()) {
                false
            } else {
                sendStoryToRepo(
                    Story(
                        getUid(),
                        getTitle(this),
                        getAuthors(this),
                        getCoverStory()?.imageUrl!!,
                        getLocationGame(),
                        getSentences()
                    )
                )
                true
            }
        }
    }

    private fun getTitle(storage: GameStorage): String = storage.getTitleStory().toString().trim()

    private fun getAuthors(storage: GameStorage): String {
        val namePlayers = StringBuilder()
        val listPlayer = storage.getPlayers()
        listPlayer.forEach {
            namePlayers.append(it.name).append(" ")
        }
        return namePlayers.toString()
    }

    private fun sendStoryToRepo(story: Story) {
        assistantModel.saveStory(story)
            .subscribe({
                saveStorySuccessStatistic(story)
                successSaveFlagLiveDate.value = true
            }, {
                saveStoryFailedStatistic(story, it)
                successSaveFlagLiveDate.value = false
            })
    }

    private fun saveStorySuccessStatistic(story: Story) {
        val storage = StoryHeroesApp.instance.gameStorage
        val time = timeFromGameCreation(storage.getTimeCreateStory()).getStringForStatistics()
        val prop = listOf(
            StatHelper.storyName to story.name,
            StatHelper.storyId to story.id.toString(),
            StatHelper.timeEvent to getCurrentDateTime().getString(),
            StatHelper.saveStoryTimeFromGameCreation to time,
            StatHelper.saveStoryCoverId to storage.getCoverStory()!!.id.toString(),
            StatHelper.saveStoryCoverName to storage.getCoverStory()!!.name,
            StatHelper.saveStoryNumberSentenceInStory to story.sentences!!.count().toString(),
            StatHelper.saveStoryNumberSymbolsInStory to getNumberSymbolsInStory(story.sentences!!).toString()
        )
        riseEvent(StatHelper.titleAndSaveScreenBtnSaveClicked, prop)
    }

    private fun saveStoryFailedStatistic(story: Story, throwable: Throwable) {
        val prop = listOf(
            StatHelper.saveStoryFailed to throwable.toString(),
            StatHelper.storyName to story.name,
            StatHelper.storyId to story.id.toString(),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.titleAndSaveScreenBtnSaveClicked, prop)
    }
}