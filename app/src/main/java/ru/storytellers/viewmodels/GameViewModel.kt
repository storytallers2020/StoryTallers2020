package ru.storytellers.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.engine.level.Levels
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.ContentTypeEnum
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.addDotIfNeed
import ru.storytellers.utils.collectSentence
import ru.storytellers.utils.getUid
import ru.storytellers.utils.trimSentenceSpace
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameViewModel(
    private val game: Game,
    levels: Levels

): BaseViewModel<DataModel>() {
    private  val listPlayer= StoryTallerApp.instance.gameStorage.getListPlayers()
    private  val listSentenceOfTale= StoryTallerApp.instance.gameStorage.getListSentenceOfTale()
    private  val levelGame= levels.getLevelById(StoryTallerApp.instance.gameStorage.getLevelGame())
    private var currentPlayer: Player?=null
    var isCorrectFlag: Boolean=true
    private var currentSentenceOfTale: SentenceOfTale?=null
    private val textResult= mutableListOf<String>()

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
        val sentence = content
            .trimSentenceSpace()
            .addDotIfNeed()


        currentPlayer?.let {
            SentenceOfTale(
                getUid(),
                it,
                game.getTurn(),
                sentence,
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
        listSentenceOfTale.apply {
            add(currentSentenceOfTale)
            textResult.clear()
            forEach {textResult.add(it!!.content)}
        }
        resultTextLiveData.value = textResult.collectSentence()

    }
}