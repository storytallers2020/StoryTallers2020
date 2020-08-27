package ru.storytellers.ui.fragments

import android.content.Context
import android.os.Bundle
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

    override fun backClicked(): Boolean = true

    override fun iniViewModel() { }

    override fun init() {
        sentence_line.addTextChangedListener(assistantFragment.getTextWatcher())

        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        reminder_intro.post { View.FOCUS_DOWN }

        button_end.setOnClickListener { onButtonEndClicked() }
        btn_send.setOnClickListener { onButtonSendClicked() }

        assistantFragment.showIntro()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assignSubscribers()
    }

    override fun onStart() {
        super.onStart()

        model.getUriBackgroundImage()
        model.onStartTurn()
    }

    private fun assignSubscribers() {
        handlerCurrentPlayerLiveData()
        handlerResultTextLiveData()
        handlerIsCorrectSentence()
        handlerUriBackgroundImage()
        handlerWordChanged()
        handlerEndGamePossible()
    }

    private fun onButtonSendClicked() {
        model.onButtonSendClicked(sentence_line.text.toString())

        assistantFragment.hideKeyboard()
        scroll_view.smoothScrollTo(0, story_body.bottom)
        sentence_line.setText("")
        assistantFragment.makeInVisibleBtnSend()
    }

    private fun handlerEndGamePossible() {
        model.subscribeOnEndGamePossibleChanged().observe(viewLifecycleOwner, Observer {
            if (it) button_end.visibility = View.VISIBLE
            else button_end.visibility = View.GONE
        })
    }

    private fun handlerCurrentPlayerLiveData() {
        model.subscribeOnPlayerChanged().observe(viewLifecycleOwner, Observer { player ->
            player_name.text = player.name
            player.character?.let {
                resourceToUri(it.avatarUrl)?.let { uri ->
                    loadImage(uri, avatar)
                }
            }
        })
    }

    private fun handlerIsCorrectSentence() {
        model.subscribeOnSentenceChecked().observe(viewLifecycleOwner, Observer {
            if (!it) toastShowLong(context, context?.getString(R.string.isnt_correct_sentence))
        })
    }

    private fun handlerResultTextLiveData() {
        model.subscribeOnTextChanged().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) assistantFragment.showGameField()
            story_body.text = it
        })
    }

    private fun handlerUriBackgroundImage() {
        model.subscribeOnBackgroundImageChanged().observe(viewLifecycleOwner, Observer {
            setBackgroundImage(it, root_element_game_cl)
        })
    }

    private fun handlerWordChanged() {
        model.subscribeOnWordChanged().observe(viewLifecycleOwner, Observer {
            mandatory_container.visibility = View.VISIBLE
            tv_mandatory_word.text = it
            mandatoryClickListener()
        })
    }

    private fun mandatoryClickListener() {
        if (!tv_mandatory_word.hasOnClickListeners())
            tv_mandatory_word.setOnClickListener {
                var text = sentence_line.text.toString()
                val mandatoryWord = tv_mandatory_word.text.toString()
                text = if (text.isBlank()) mandatoryWord
                else "$text $mandatoryWord"
                sentence_line.setText(text)
                sentence_line.setSelection(text.length)
            }
    }

    private fun onButtonEndClicked() {
        router.navigateTo(Screens.GameEndScreen())
    }

}