package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Player
import ru.storytellers.utils.StatHelper
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.utils.getCurrentDateTime
import ru.storytellers.utils.getString
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class TeamCharacterViewModel : BaseViewModel<DataModel>() {
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
            StatHelper.playerCount to listPlayers.count().toString(),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.teamScreenBtnToLocationScreen, prop)
    }

    fun onGotoCharacterScreen() {
        riseEvent(StatHelper.teamScreenAddPlayerClicked)
    }
}