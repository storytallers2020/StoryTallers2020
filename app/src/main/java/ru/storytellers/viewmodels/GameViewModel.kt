package ru.storytellers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.engine.level.Levels
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.ContentTypeEnum
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.ui.assistant.GameViewModelAssistant
import ru.storytellers.utils.getUid
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameViewModel(
    private val game: Game,
    private val viewModelAssistant: GameViewModelAssistant,
    levels: Levels

): BaseViewModel<DataModel>() {
    private  val listPlayer= StoryTallerApp.instance.gameStorage.getListPlayers()
    private  val listSentenceOfTale= StoryTallerApp.instance.gameStorage.getListSentenceOfTale()
    private  val levelGame= levels.getLevelById(StoryTallerApp.instance.gameStorage.getLevelGame())
    private var stringBuilder=StringBuilder()

    private val currentPlayerLiveData = MutableLiveData<Player>()
    private val resultTextLiveData = MutableLiveData<String>()
    private var currentPlayer: Player?=null
    private var currentSentenceOfTale: SentenceOfTale?=null

    fun getCurrentPlayer(){
        currentPlayer=game.getCurrentPlayer()
        currentPlayerLiveData.value=currentPlayer
    }
    fun createNewGame(){
        levelGame?.let { game.newGame(listPlayer, it ) }
    }

    fun subscribeOnCurrentPlayer(): LiveData<Player> {

        return currentPlayerLiveData
    }
    fun subscribeOnResultText(): LiveData<String> {

        return resultTextLiveData
    }

    fun createSentenceOfTale(content:String) {
        currentPlayer?.let {
            SentenceOfTale(
                getUid(),
                it,
                game.getTurn(),
                content,
                ContentTypeEnum.TEXT
            )
        }.let { currentSentenceOfTale = it }
        checkCurrentSentenceOfTale()
    }

    private fun checkCurrentSentenceOfTale(){
       val checkResult= currentSentenceOfTale?.let { game.nextStep(it) }
        checkResult?.let {
            if (it){
                handlerCurrentSentenceOfTale()
            } else{
                //что делаем если предложение проверку не прошло?
                //кидаем тост пока что
            }
        }
    }

    private fun handlerCurrentSentenceOfTale(){
        listSentenceOfTale.add(currentSentenceOfTale)
        stringBuilder.append(currentSentenceOfTale?.content).append(" ")
        if (stringBuilder.isNotEmpty()) {
            resultTextLiveData.value = stringBuilder.toString()
        }
    }
}