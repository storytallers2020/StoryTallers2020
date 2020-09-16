package ru.storytellers.viewmodels

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.ICharacterRepository
import ru.storytellers.utils.*
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class CharacterCreateViewModel(
    private val characterRepository: ICharacterRepository,
    private val playerCreator: PlayerCreator
) : BaseViewModel<DataModel>() {

    var isCharacterSelected = false
    var isNameEntered = false
    private lateinit var namePlayer: String
    private var flagNameIsNormal = 0
    private var flagNameIsShort = 1
    private var flagNameIsGaps = 2
    private val app = StoryTallerApp.instance
    private val storage = app.gameStorage

    private val onStatusNameEnteredLiveData = MutableLiveData<Boolean>()
    private val onStatusCharacterSelectedLiveData = MutableLiveData<Boolean>()
    private val onNameIncorrectLiveData = MutableLiveData<Int>()
    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Character>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    private var listPlayers: MutableList<Player> = storage.getPlayers()

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Character>> {
        return onSuccessLiveData
    }

    fun subscribeOnCharacterSelected(): LiveData<Boolean> {
        return onStatusCharacterSelectedLiveData
    }

    fun subscribeOnStatusNameEntered(): LiveData<Boolean> {
        return onStatusNameEnteredLiveData
    }

    fun subscribeOnNameIncorrect(): LiveData<Int> {
        return onNameIncorrectLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    private fun addPlayer(player: Player) {
        listPlayers.add(player)
    }

    private fun setNamePlayer(name: String) {
        playerCreator.setNamePlayer(name)
    }

    private fun setCharacterOfPlayer(character: Character) {
        playerCreator.setCharacterOfPlayer(character)
    }

    private fun getPlayer() = playerCreator.getPlayer()

    fun getAllCharacters() {
        characterRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

    private fun onPlayerAddedStat(player: Player) {
        val prop = listOf(
            Pair(StatHelper.playerName, player.name),
            Pair(StatHelper.playerId, player.id.toString()),
            Pair(StatHelper.characterName, player.character?.name ?: ""),
            Pair(StatHelper.timeEvent, getCurrentDateTime().getString())
        )
        riseEvent(StatHelper.characterScreenBtnPlayerSaved, prop)
    }

    fun transferNamePlayer(text: Editable?) {
        namePlayer = text.toString()
        onNameIncorrectLiveData.value = namePlayerValidation()
    }

    private fun namePlayerValidation(): Int {
        if (namePlayer.length < 3) {
            isNameEntered = false
            setStatusNameEnteredLiveData(isNameEntered)
            return flagNameIsShort
        }
        if (namePlayer.stringNotContainSymbols()) {
            isNameEntered = false
            setStatusNameEnteredLiveData(isNameEntered)
            return flagNameIsGaps
        }
        setNamePlayer(namePlayer)
        addPlayerLocal()
        isNameEntered = true
        setStatusNameEnteredLiveData(isNameEntered)
        return flagNameIsNormal
    }

    private fun setStatusNameEnteredLiveData(nameEntered: Boolean) {
        onStatusNameEnteredLiveData.value = nameEntered
    }

    fun characterSelected(character: Character) {
        isCharacterSelected = true
        setCharacterOfPlayer(character)
        onStatusCharacterSelectedLiveData.value = isCharacterSelected
    }

    private fun addPlayerLocal() {
        getPlayer()?.let { player ->
            addPlayer(player)
            onPlayerAddedStat(player)
        }
    }
}