package ru.storytellers.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.sentence_input_layout.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.image.IImageLoader
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.assistant.GameFragmentAssistant
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.hideSoftKey
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.viewmodels.GameViewModel

class GameFragment : BaseFragment<DataModel>() {

    private val assistantFragment: GameFragmentAssistant by lazy { GameFragmentAssistant(this@GameFragment) }
    override val model: GameViewModel by inject()
    override val layoutRes = R.layout.fragment_game

    var inputMethodManager: Any? = null
    private var isInputContentCorrect = false
    private val focusListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            hideSoftKey(v)
        }
    }

    override fun backClicked(): Boolean = true

    override fun initViewModel() {}

    override fun init() {
        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        sentence_line.onFocusChangeListener = focusListener
        reminder_intro.post { View.FOCUS_DOWN }
        button_end.setOnClickListener { onButtonEndClicked() }
        btn_send.setOnClickListener { onButtonSendClicked() }
        assignSubscribers()
        assistantFragment.showIntro()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.getUriBackgroundImage()
        model.onStartTurn()
    }

    private fun assignSubscribers() {
        handlerCurrentPlayerLiveData()
        handlerTextChangedLiveData()
        handlerIsCorrectSentence()
        handlerUriBackgroundImage()
        handlerWordChanged()
        handlerEndGamePossible()
        handlerInputIncorrect()
    }

    private fun onButtonSendClicked() {
        model.onButtonSendClicked(sentence_line.text.toString())
        assistantFragment.hideKeyboard()
        if (statusCheck() && handlerIsCorrectSentence()) {
            scroll_view.smoothScrollTo(0, story_body.bottom)
            sentence_line.setText("")
            isInputContentCorrect = false
        }
    }

    private fun handlerInputIncorrect() {
        model.inputValid.subscribeOnInputIncorrect().observe(viewLifecycleOwner, {
            when (it) {
                1 -> {
                    setError(getString(R.string.err_short_sentence))
                }
                2 -> {
                    setError(getString(R.string.err_sentence_is_gaps))
                }
                else -> {
                    et_step.error = null
                    et_step.isErrorEnabled = false
                    isInputContentCorrect = true
                }
            }
        })
    }

    private fun handlerEndGamePossible() {
        model.subscribeOnEndGamePossibleChanged().observe(viewLifecycleOwner, {
            if (it) button_end.visibility = View.VISIBLE
            else button_end.visibility = View.GONE
        })
    }

    private fun handlerCurrentPlayerLiveData() {
        model.subscribeOnPlayerChanged().observe(viewLifecycleOwner, { player ->
            player_name.text = player.name
            player.character?.let {
                imageLoader.loadInto(it.avatarUrl, R.drawable.avatar_stub, avatar)
//                resourceToUri(it.avatarUrl)?.let { uri ->
//                    loadImage(uri, avatar)
//                }
            }
        })
    }

    private fun handlerIsCorrectSentence(): Boolean {
        var isCorrect = true
        model.subscribeOnSentenceChecked().observe(viewLifecycleOwner, {
            if (!it) {
                setError(getString(R.string.err_mandatory_word_missing))
            }
            isCorrect = it
        })
        return isCorrect
    }

    private fun handlerTextChangedLiveData() {
        model.subscribeOnTextChanged().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) assistantFragment.showGameField()
            story_body.text = it
        })
    }

    private fun handlerUriBackgroundImage() {
        model.subscribeOnBackgroundImageChanged().observe(viewLifecycleOwner, {
            setBackground(it)
        })
    }

    private fun handlerWordChanged() {
        model.subscribeOnWordChanged().observe(viewLifecycleOwner, {
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
                try {
                    sentence_line.setSelection(text.length)
                } catch (e: Exception) {
                    sentence_line.setSelection(resources.getInteger(R.integer.max_length_sentence))
                }
            }
    }

    private fun setError(nameError: String) {
        et_step.error = nameError
    }

    private fun statusCheck() = isInputContentCorrect

    private fun onButtonEndClicked() {
        model.onButtonEndGameClickedStatistic()
        router.navigateTo(Screens.GameEndScreen())
    }

}