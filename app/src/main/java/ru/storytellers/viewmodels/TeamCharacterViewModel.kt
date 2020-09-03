package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Player
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.utils.toProperties
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class TeamCharacterViewModel : BaseViewModel<DataModel>() {
    private val app = StoryTallerApp.instance
    private val storage = app.gameStorage

    private var listPlayers: MutableList<Player> =
        StoryTallerApp.instance.gameStorage.getPlayers()
    private val playersLiveData = MutableLiveData<List<Player>>()

    fun subscribeOnPlayers(): LiveData<List<Player>> {
        playersLiveData.value = listPlayers
        return playersLiveData
    }

    fun removePlayer(player: Player) {
        listPlayers.remove(player)
        playersLiveData.value = listPlayers
    }

    fun onGotoLocationScreen() {
        val prop = listOf(
            Pair(StatHelper.playerCount, listPlayers.count().toString()),
            Pair(StatHelper.time, getCurrentDateTime().getString())
        )
        app.stat.riseEvent(StatHelper.characterScreenNextClicked, prop.toProperties())
    }

    fun onGotoCharacterScreen() {
        app.stat.riseEvent(StatHelper.addPlayerClicked)
    }

}