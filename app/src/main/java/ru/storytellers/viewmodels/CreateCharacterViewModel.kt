package ru.storytellers.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Player
import ru.storytellers.model.repository.ICharacterRepository

class CreateCharacterViewModel(private val characterRepository: ICharacterRepository) : BaseViewModel<DataModel>() {


    private val onSuccessliveData = MutableLiveData<DataModel.Success<Character>>()
    private val onErrorliveData = MutableLiveData<DataModel.Error>()
    private val onLoadingliveData = MutableLiveData<DataModel.Loading>()
    private val playersLiveData = MutableLiveData<List<Player>>()
    private val listPlayers= mutableListOf<Player>()


     fun subscribeOnSuccess(): LiveData<DataModel.Success<Character>>{
         return onSuccessliveData
     }
    fun subscribeOnError(): LiveData<DataModel.Error>{
        return onErrorliveData
    }
    fun subscribeOnLoading() : LiveData<DataModel.Loading>{
        return onLoadingliveData
    }
    fun subscribeOnPlayers(): LiveData<List<Player>>{
        playersLiveData.value=listPlayers
        return playersLiveData
    }

    fun addPlayer(player:Player){
        listPlayers.add(player)
    }



    fun getAllCharacters(){
        characterRepository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccessliveData.value=DataModel.Success(it)
            },{
                onErrorliveData.value=DataModel.Error(it)
            })
    }

    override fun subscribe(): LiveData<DataModel> {
        TODO("Not yet implemented")
    }

}


