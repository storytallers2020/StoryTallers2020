package ru.storytellers.viewmodels

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryHeroesApp
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
    val inputValid: InputValidation by lazy { InputValidation() }

    var isCharacterSelected = false
    private lateinit var namePlayer: String

    private val app = StoryHeroesApp.instance
    private val storage = app.gameStorage

    private val onStatusCharacterSelectedLiveData = MutableLiveData<Boolean>()

    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Character>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    private var listPlayers: MutableList<Player> = storage.getPlayers()

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Character>> {
        return onSuccessLiveData
    }

    fun subscribeOnCharacterSelected(): LiveData<Boolean> {
        return onStatusCharacterSelectedLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    private fun addPlayer(player: Player) {
        listPlayers.add(player)
    }

    private fun setNamePlayer(name: String) {
        playerCreator.setNamePlayer(name.trimSentenceSpace())
    }

    private fun setCharacterOfPlayer(character: Character) {
        playerCreator.setCharacterOfPlayer(character)
    }

    private fun getPlayer() = playerCreator.getPlayer()

    fun getAllCharacters() {
        setTrueInProgressEnableLiveData()
        characterRepository.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
                setFalseInProgressEnableLiveData()
            }, {
                onErrorLiveData.value = DataModel.Error(it)
                setFalseInProgressEnableLiveData()
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
        validationCheck()
    }

    private fun validationCheck() {
        if (inputValid.inputValidation(namePlayer) == 0 && isCharacterSelected) {
            setNamePlayer(namePlayer)
            addPlayerLocal()
        }
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