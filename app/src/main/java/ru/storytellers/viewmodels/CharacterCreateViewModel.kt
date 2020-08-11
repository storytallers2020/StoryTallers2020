package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.ICharacterRepository
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class CharacterCreateViewModel(private val characterRepository: ICharacterRepository,private val playerCreator: PlayerCreator) :
    BaseViewModel<DataModel>() {

    private val onSuccessliveData = MutableLiveData<DataModel.Success<Character>>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()
    private val onLoadingliveData = MutableLiveData<DataModel.Loading>()
    private val playersLiveData = MutableLiveData<List<Player>>()
    private val flagActiveLiveData = MutableLiveData<Boolean>()

    private var listPlayers: MutableList<Player> = StoryTallerApp.instance.gameStorage.getListPlayers()
    private var flagActive: Boolean=false

    fun subscribeOnSuccess(): LiveData<DataModel.Success<Character>> {
        return onSuccessliveData
    }

    fun subscribeOnError(): LiveData<DataModel.Error> {
        return onErrorliveData
    }
    fun subscribeOnLoading() : LiveData<DataModel.Loading> {
        return onLoadingliveData
    }

    fun addPlayer(player:Player){
        listPlayers.add(player)
    }

    fun setNamePlayer(name: String) {
        playerCreator.setNamePlayer(name)
    }

    fun setCharacterOfPlayer(character: Character )
    {
        playerCreator.setCharacterOfPlayer(character)
    }

    fun createPlayer()=playerCreator.createPlayer()


    fun getAllCharacters(){
        characterRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessliveData.value=DataModel.Success(it)
            },{
                onErrorliveData.value=DataModel.Error(it)
            })
    }
}