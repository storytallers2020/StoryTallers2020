package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.ICharacterRepository
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class CharacterCreateViewModel(
    private val characterRepository: ICharacterRepository,
    private val playerCreator: PlayerCreator
) :
    BaseViewModel<DataModel>() {

    private val app = StoryTallerApp.instance
    private val storage = app.gameStorage

    private val onSuccessLiveData = MutableLiveData<DataModel.Success<Character>>()
    private val onErrorLiveData = MutableLiveData<DataModel.Error>()

    private var listPlayers: MutableList<Player> = storage.getPlayers()

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Character>> {
        return onSuccessLiveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorLiveData
    }

    fun addPlayer(player: Player) {
        listPlayers.add(player)
    }

    fun setNamePlayer(name: String) {
        playerCreator.setNamePlayer(name)
    }

    fun setCharacterOfPlayer(character: Character) {
        playerCreator.setCharacterOfPlayer(character)
    }

    fun getPlayer() = playerCreator.getPlayer()


    fun getAllCharacters() {
        characterRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessLiveData.value = DataModel.Success(it)
            }, {
                onErrorLiveData.value = DataModel.Error(it)
            })
    }

    fun onPlayerAdded(player: Player) {
        val prop = listOf(
            Pair(StatHelper.playerName, player.name),
            Pair(StatHelper.playerId, player.id.toString()),
            Pair(StatHelper.characterName, player.character?.name ?: ""),
            Pair(StatHelper.time, getCurrentDateTime().getString())
        )
        app.stat.riseEvent(StatHelper.onPlayerAdded, prop.toProperties())

    }
}