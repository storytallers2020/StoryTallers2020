package ru.storytellers.ui.fragments

import android.content.Context
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.sentence_input_layout.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.assistant.GameFragmentAssistant
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.utils.setBackgroundImage
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.GameViewModel

class GameFragment : BaseFragment<DataModel>() {

    private val assistantFragment: GameFragmentAssistant by lazy { GameFragmentAssistant(this@GameFragment) }
    override val model: GameViewModel by inject()
    override val layoutRes = R.layout.fragment_game
    var inputMethodManager: Any? = null
    private lateinit var textWatcher: TextWatcher
    var textSentenceOfTale: String? = null
    private var textResultStoryTaller: String? = null

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun iniViewModel() {}

    override fun init() {
        model.createNewGame()
        model.getCurrentPlayer()
        model.getUriBackgroundImage()

        textWatcher = assistantFragment.getTextWatcher()
        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        reminder_intro.post { View.FOCUS_DOWN }
        button_end.setOnClickListener { navigateToGameEndScreen() }
        btn_send.setOnClickListener { handlerBtnSend() }
        sentence_line.addTextChangedListener(textWatcher)
        assignSubscribers()
        assistantFragment.showIntro()
        model.onStartTurn()
    }

    private fun assignSubscribers() {
        handlerCurrentPlayerLiveData()
        handlerResultTextLiveData()
        handlerIsCorrectSentence()
        handlerUriBackgroundImage()
        handlerWordChanged()
    }

    private fun handlerBtnSend() {
        textSentenceOfTale?.let { model.createSentenceOfTale(it) }
        assistantFragment.hideKeyboard()
        scroll_view.smoothScrollTo(0, story_body.bottom)
        sentence_line.setText("")
        assistantFragment.makeInVisibleBtnSend()
        assistantFragment.showGameField()
        model.getCurrentPlayer()
    }

    private fun handlerCurrentPlayerLiveData() {
        model.subscribeOnCurrentPlayer().observe(viewLifecycleOwner, Observer { player ->
            player_name.text = player.name
            player.character?.let {
                resourceToUri(it.avatarUrl)?.let { uri ->
                    loadImage(uri, avatar)
                }
            }
        })
    }

    private fun handlerIsCorrectSentence() {
        model.subscribeOnIsCorrectFlag().observe(viewLifecycleOwner, Observer {
            context?.let { context -> toastShowLong(context, "Isn`t correct sentence") }
            model.isCorrectFlag = true
        })
    }

    private fun handlerResultTextLiveData() {
        model.subscribeOnResultText().observe(viewLifecycleOwner, Observer {
            textResultStoryTaller = it
            story_body.text = it
        })
    }

    private fun handlerUriBackgroundImage() {
        model.subscribeOnUriBackgroundImage().observe(viewLifecycleOwner, Observer {
            setBackgroundImage(it, root_element_game_cl)
        })
    }

    private fun handlerWordChanged() {
        model.subscribeOnWord().observe(viewLifecycleOwner, Observer {
            mandatory_container.visibility = View.VISIBLE
            tv_mandatory_word.text = it
            mandatoryClickListener(it)
        })
    }

    private fun mandatoryClickListener(mandatoryWord: String) {
        tv_mandatory_word.setOnClickListener {
            with (
                with(sentence_line.text.toString()) {
                    if (this.isNotBlank()) "$this $mandatoryWord" else mandatoryWord
                }
            ) {
                sentence_line.setText(this)
                sentence_line.setSelection(this.length)
            }
        }
    }

    private fun navigateToGameEndScreen() {
        router.navigateTo(Screens.GameEndScreen())
    }

}