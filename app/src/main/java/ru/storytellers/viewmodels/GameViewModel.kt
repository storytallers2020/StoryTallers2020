package ru.storytellers.viewmodels

import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.engine.level.Level
import ru.storytellers.engine.wordRules.IWordRule
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.ContentTypeEnum
import ru.storytellers.model.entity.Player
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.utils.*
import ru.storytellers.utils.StatHelper.Companion.riseEvent
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameViewModel(private val game: Game) : BaseViewModel<DataModel>() {
    val inputValid: InputValidation by lazy { InputValidation() }
    private val app = StoryTallerApp.instance
    private val storage = app.gameStorage

    private val currentPlayerLiveData = MutableLiveData<Player>()
    private val storyTextLiveData = MutableLiveData<String>()
    private val isSentenceCorrectLiveData = MutableLiveData<Boolean>()
    private val uriBackgroundImageLiveData = MutableLiveData<Uri>()
    private val wordLiveData = MutableLiveData<String>()
    private val isEndGamePossible = MutableLiveData<Boolean>()

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
        validationCheck(content)
    }

    private fun validationCheck(content: String) {
        if (inputValid.inputValidation(content) == 0) {
            val text = content
                .trimSentenceSpace()
                .addDotIfNeed()
            val sentence = createSentence(text)
            tryGotoNextTurn(sentence)
        }
    }

    private fun createSentence(sentence: String): SentenceOfTale =
        SentenceOfTale(
            getUid(),
            game.getCurrentPlayer(),
            game.turn,
            sentence,
            ContentTypeEnum.TEXT
        )

    private fun tryGotoNextTurn(sentence: SentenceOfTale) {
        val result = game.nextStep(sentence)
        if (result) {
            checkIsEndGamePossible()
            storage.getSentences().add(sentence)
            onStartTurn()
            isSentenceCorrectLiveData.value = true
            onButtonSendClickedStatistic(sentence)
        } else {
            isSentenceCorrectLiveData.value = false
        }
    }

    fun onStartTurn() {
        updateStoryText()
        currentPlayerLiveData.value = game.getCurrentPlayer()

        storage.level?.let { level ->
            if (level.wordRule.isNeedUseWord())
                wordLiveData.value = level.wordRule.getRandomWord()
        }
    }

    private fun updateStoryText() {
        storage.level?.let { level ->
            val listOfStrings = storage.getSentences()
                .getSortedList()
                .toListOfStrings()

            storyTextLiveData.value = level
                .showRule
                .getText(game.turn, listOfStrings)
        }
    }

    private fun checkIsEndGamePossible() {
        isEndGamePossible.value = game.turn > storage.getPlayers().size
    }

    private fun onButtonSendClickedStatistic(sentence: SentenceOfTale) {
        val prop = listOf(
            StatHelper.gamePlayerId to sentence.player?.id.toString(),
            StatHelper.gamePlayerName to sentence.player?.name,
            StatHelper.timeEvent to getCurrentDateTime().getString(),
            StatHelper.turn to sentence.step.toString()
        )
        riseEvent(StatHelper.gameScreenBtnSendClicked, prop as List<Pair<String, String>>)
    }

    fun onButtonEndGameClickedStatistic() {
        val prop = listOf(
            StatHelper.totalNumberSentencesInStory to storage.getSentences().count().toString(),
            StatHelper.timeEvent to getCurrentDateTime().getString()
        )
        riseEvent(StatHelper.gameScreenBtnEndGameClicked, prop)
    }

}