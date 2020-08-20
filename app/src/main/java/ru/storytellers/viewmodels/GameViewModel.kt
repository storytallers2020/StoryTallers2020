package ru.storytellers.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.engine.level.Levels
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.ContentTypeEnum
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameViewModel(
    private val game: Game,
    levels: Levels

) : BaseViewModel<DataModel>() {
    private val listPlayer = StoryTallerApp.instance.gameStorage.getListPlayers()
    private val listSentenceOfTale = StoryTallerApp.instance.gameStorage.getListSentenceOfTale()
    private val levelGame = levels.getLevelById(StoryTallerApp.instance.gameStorage.getLevelGame())
    private var currentPlayer: Player? = null
    var isCorrectFlag: Boolean = true
    private var currentSentenceOfTale: SentenceOfTale? = null

    private val currentPlayerLiveData = MutableLiveData<Player>()
    private val resultTextLiveData = MutableLiveData<String>()
    private val isCorrectFlagLiveData = MutableLiveData<Boolean>()
    private val uriBackgroundImageLiveData = MutableLiveData<Uri>()
    private val wordLiveData = MutableLiveData<String>()
    private val checkTurnGameLiveData = MutableLiveData<Boolean>()


    fun getCurrentPlayer() {
        currentPlayer = game.getCurrentPlayer()
        currentPlayerLiveData.value = currentPlayer
    }

    fun createNewGame() {
        levelGame?.let { game.newGame(listPlayer, it) }
    }

    fun getUriBackgroundImage() {
        StoryTallerApp
            .instance
            .gameStorage
            .getLocationGame()?.imageUrl?.let {
                uriBackgroundImageLiveData.value = resourceToUri(it)
            }
    }

    fun subscribeOnCurrentPlayer() = currentPlayerLiveData
    fun subscribeOnResultText() = resultTextLiveData
    fun subscribeOnIsCorrectFlag() = isCorrectFlagLiveData
    fun subscribeOnUriBackgroundImage() = uriBackgroundImageLiveData
    fun subscribeOnWord() = wordLiveData
    fun subscribeOnCheckTurnGame() = checkTurnGameLiveData

    fun createSentenceOfTale(content: String) {
        val sentence = content
            .trimSentenceSpace()
            .addDotIfNeed()

        currentSentenceOfTale = SentenceOfTale(
            getUid(),
            currentPlayer,
            game.getTurn(),
            sentence,
            ContentTypeEnum.TEXT
        )
        compareTurnGameWithNumberOfPlayers()
        checkCurrentSentenceOfTale()
    }

    private fun checkCurrentSentenceOfTale() {
        val checkResult = currentSentenceOfTale?.let { game.nextStep(it) }
        checkResult?.let {
            if (it) {
                handlerCurrentSentenceOfTale()
            } else {
                isCorrectFlag = it
                isCorrectFlagLiveData.value = isCorrectFlag
            }
        }
    }

    private fun handlerCurrentSentenceOfTale() {
        currentSentenceOfTale?.let { listSentenceOfTale.add(it) }

        getCurrentLevel()?.let { level ->

            val listOfStrings = listSentenceOfTale
                .toList()
                .getSortedList()
                .toListOfStrings()

            resultTextLiveData.value =
                level
                    .showRule
                    .convert(game.getTurn(), listOfStrings)

            onStartTurn()
        }
    }

    fun onStartTurn() {
        getCurrentLevel()?.let {level ->
            if (level.wordRule.isNeedUseWord())
                wordLiveData.value = level.wordRule.getRandomWord()
        }
    }

    private fun compareTurnGameWithNumberOfPlayers(){
        checkTurnGameLiveData.value=game.getTurn()==listPlayer.size
    }

}