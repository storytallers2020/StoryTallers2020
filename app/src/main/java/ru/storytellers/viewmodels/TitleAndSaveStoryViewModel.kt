package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.GameStorage
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.assistant.TitleAndSaveModelAssistant
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel


class TitleAndSaveStoryViewModel(
    private val assistantModel: TitleAndSaveModelAssistant
) : BaseViewModel<DataModel>() {
    private var titleStory: String? = null
    private val coverLiveDate = MutableLiveData<Cover>()
    private val successSaveFlagLiveDate = MutableLiveData<Boolean>()

    fun setTitleStory(title: String) {
        titleStory = title
        StoryTallerApp.instance.gameStorage.setTitleStory(titleStory!!)
    }

    fun subscribeOnSuccessSaveFlag() = successSaveFlagLiveDate

    fun subscribeOnCover(): LiveData<Cover> {
        coverLiveDate.value = StoryTallerApp.instance.gameStorage.getCoverStoryTaller()
        return coverLiveDate
    }

    fun createStoryTaller() {
        var story: Story
        val gameStorage = StoryTallerApp.instance.gameStorage
        with(gameStorage) {
            story = Story(
                getUid(),
                getTitle(this),
                getAuthors(this),
                this.getCoverStoryTaller()?.imageUrl!!,
                this.getLocationGame(),
                this.getSentences()
            )
        }
        saveStory(story)
    }

    private fun getTitle(storage: GameStorage): String {
        var title = "no title"
        storage.getTitleStory()?.let { title = it }
        return title
    }

    private fun getAuthors(storage: GameStorage): String {
        val namePlayers = StringBuilder()
        val listPlayer = storage.getPlayers()
        listPlayer.forEach {
            namePlayers.append(it.name).append(" ")
        }
        return namePlayers.toString()
    }

    private fun saveStory(story: Story) {
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
        val prop = listOf(
            StatHelper.storyName to story.name,
            StatHelper.storyId to story.id.toString(),
            StatHelper.saveStoryTime to getCurrentDateTime().getString()
        )
        stat.riseEvent(StatHelper.onTitleAndSaveStoryScreen, prop.toProperties())
    }

    private fun saveStoryFailedStatistic(story: Story, throwable: Throwable) {
        val prop = listOf(
            StatHelper.saveStoryFailed to throwable.toString(),
            StatHelper.storyName to story.name,
            StatHelper.storyId to story.id.toString(),
            StatHelper.saveStoryTime to getCurrentDateTime().getString()
        )
        stat.riseEvent(StatHelper.onTitleAndSaveStoryScreen, prop.toProperties())
    }

    fun buttonSaveStoryClickedStatistic() {
        stat.riseEvent(StatHelper.buttonSaveStoryClicked)
    }

}