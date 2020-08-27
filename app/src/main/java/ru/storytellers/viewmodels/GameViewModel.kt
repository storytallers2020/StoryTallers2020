package ru.storytellers.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.ContentTypeEnum
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.*
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameViewModel(private val game: Game) : BaseViewModel<DataModel>() {

    private val storage = StoryTallerApp.instance.gameStorage
    //private val listPlayer = StoryTallerApp.instance.gameStorage.getListPlayers()
    //private val listSentenceOfTale = StoryTallerApp.instance.gameStorage.getListSentenceOfTale()
    //private val levelGame = levels.getLevelById(StoryTallerApp.instance.gameStorage.getLevelGame())
    //private var currentPlayer: Player? = null

    //var isCorrectFlag: Boolean = true
    //private var currentSentenceOfTale: SentenceOfTale? = null

    private val currentPlayerLiveData = MutableLiveData<Player>()
    private val storyTextLiveData = MutableLiveData<String>()
    private val isSentenceCorrectLiveData = MutableLiveData<Boolean>()
    private val uriBackgroundImageLiveData = MutableLiveData<Uri>()
    private val wordLiveData = MutableLiveData<String>()
    private val isEndGamePossible = MutableLiveData<Boolean>()


//    fun getCurrentPlayer() {
//        currentPlayer = game.getCurrentPlayer()
//        currentPlayerLiveData.value = currentPlayer
//    }

    fun createNewGame() {
        storage.level?.let {level ->
            game.newGame(storage.getPlayers(), level)
        }
    }

    fun getUriBackgroundImage() {
        storage.location?.let {
            uriBackgroundImageLiveData.value = resourceToUri(it.imageUrl)
        }
    }

    fun subscribeOnPlayerChanged(): LiveData<Player> = currentPlayerLiveData
    fun subscribeOnTextChanged(): LiveData<String> = storyTextLiveData
    fun subscribeOnSentenceChecked(): LiveData<Boolean> = isSentenceCorrectLiveData
    fun subscribeOnBackgroundImageChanged(): LiveData<Uri> = uriBackgroundImageLiveData
    fun subscribeOnWordChanged(): LiveData<String> = wordLiveData
    fun subscribeOnEndGamePossibleChanged(): LiveData<Boolean> = isEndGamePossible

    fun onButtonSendClicked(content: String) {

        val text = content
            .trimSentenceSpace()
            .addDotIfNeed()

        val sentence = createSentence(text)
        tryGotoNextTurn(sentence)
    }

    private fun createSentence(sentence: String): SentenceOfTale =
        SentenceOfTale(
            getUid(),
            game.getCurrentPlayer(),
            game.getTurn(),
            sentence,
            ContentTypeEnum.TEXT
        )

    // Удалить isCorrectFlag нахер!!!
    // Прицепить onStartTurn и onEndTurn
    // Расплести клубок с добавлением предложения
    private fun tryGotoNextTurn(sentence: SentenceOfTale) {
        val result = game.nextStep(sentence)
        if (result) {
            checkIsEndGamePossible()
            storage.getSentences().add(sentence)
            updateStoryText()
            onStartTurn()
            isSentenceCorrectLiveData.value = true
        } else {
            //isCorrectFlag = it
            isSentenceCorrectLiveData.value = false
        }
    }

    private fun updateStoryText() {
        storage.level?.let { level ->
            val listOfStrings = storage.getSentences()
                .getSortedList()
                .toListOfStrings()

            storyTextLiveData.value = level
                .showRule
                .getText(game.getTurn(), listOfStrings)
        }
    }

    fun onStartTurn() {
        currentPlayerLiveData.value = game.getCurrentPlayer()

        storage.level?.let { level ->
            if (level.wordRule.isNeedUseWord())
                wordLiveData.value = level.wordRule.getRandomWord()
        }
    }

    private fun checkIsEndGamePossible() {
        isEndGamePossible.value = game.getTurn() > storage.getPlayers().size
    }

}