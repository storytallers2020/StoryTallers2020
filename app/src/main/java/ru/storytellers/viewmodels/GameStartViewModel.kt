package ru.storytellers.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.engine.Game
import ru.storytellers.model.DataModel
import ru.storytellers.utils.*
import ru.storytellers.utils.StatHelper.Companion.getNameLevel
import ru.storytellers.utils.StatHelper.Companion.getNamePlayersAndCharacter
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameStartViewModel(private val game: Game) : BaseViewModel<DataModel>() {
    private val app = StoryHeroesApp.instance
    private val storage = app.gameStorage
    private val levelGameLiveData = MutableLiveData<Int>()
    private val uriBackgroundImageLiveData = MutableLiveData<Uri>()

    fun requestGameLevelFromStorage() {
        levelGameLiveData.value = StoryHeroesApp.instance.gameStorage.level?.id ?: 0
    }

    fun subscribeOnLevelGame() = levelGameLiveData

    fun createNewGame() {
        storage.level?.let { level ->
            game.newGame(storage.getPlayers(), level)
        }
    }

    fun buttonStartClickedStatistic() {
        val time = timeFromGameCreation(storage.getTimeCreateStory()).getStringForStatistics()
        val namePlayers = getNamePlayersAndCharacter(storage.getPlayers())
        val prop = listOf(
            StatHelper.timeEvent to getCurrentDateTime().getString(),
            StatHelper.gameStartTimeFromGameCreation to time, /*промежуток времени от момента создания сказки в минутах и секундах*/
            StatHelper.gameStartLevelGame to getNameLevel(storage.level!!.id),
            StatHelper.gameStartNumberPlayersInGame to storage.getPlayers().count().toString(),
            StatHelper.gameStartLocationGame to storage.getLocationGame()?.name.toString(),
            StatHelper.gameStartNamePlayersAndCharacter to namePlayers
        )
        riseEvent(StatHelper.gameStartScreenBtnStartClicked, prop)
    }

    fun getUriBackgroundImage() {
        storage.location?.let {
            uriBackgroundImageLiveData.value = resourceToUri(it.imageUrl)
        }
    }

    fun subscribeOnBackgroundImageChanged(): LiveData<Uri> = uriBackgroundImageLiveData

}