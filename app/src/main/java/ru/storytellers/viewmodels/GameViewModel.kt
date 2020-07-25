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
    private var currentPlayer: Player?=null
    var isCorrectFlag: Boolean=true
    private var currentSentenceOfTale: SentenceOfTale?=null

    private val currentPlayerLiveData = MutableLiveData<Player>()
    private val resultTextLiveData = MutableLiveData<String>()
    private val isCorrectFlagLiveData = MutableLiveData<Boolean>()


    fun getCurrentPlayer(){
        currentPlayer=game.getCurrentPlayer()
        currentPlayerLiveData.value=currentPlayer
    }
    fun createNewGame(){
        levelGame?.let { game.newGame(listPlayer, it ) }
    }

    fun subscribeOnCurrentPlayer()=currentPlayerLiveData
    fun subscribeOnResultText()=resultTextLiveData
    fun subscribeOnIsCorrectFlag()=isCorrectFlagLiveData

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
                isCorrectFlag=it
                isCorrectFlagLiveData.value=isCorrectFlag

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