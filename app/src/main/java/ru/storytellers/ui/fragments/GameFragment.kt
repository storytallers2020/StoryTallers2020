package ru.storytellers.ui.fragments

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_character_create_v3.*
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.item_image_character_create.view.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.assistant.GameFragmentAssistant
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.loadImage
import ru.storytellers.utils.resourceToUri
import ru.storytellers.viewmodels.GameViewModel

class GameFragment: BaseFragment<DataModel>() {

    private  val assistantFragment: GameFragmentAssistant by lazy { GameFragmentAssistant(this@GameFragment) }
    override val model: GameViewModel by inject()
    override val layoutRes = R.layout.fragment_game
    var inputMethodManager: Any?= null
    private lateinit var textWatcher: TextWatcher
    var textSentenceOfTale: String?=null

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun iniViewModel() {
    }

    override fun init() {
        model.createNewGame()
        model.getCurrentPlayer()

        textWatcher=assistantFragment.getTextWatcher()
        inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        reminder_intro.post{ View.FOCUS_DOWN }
        button_end.setOnClickListener { navigateToGameEndScreen() }
        btn_send.setOnClickListener {handlerBtnSend()}
        sentence_line.addTextChangedListener(textWatcher)
        assignSubscribers()
        assistantFragment.showIntro()
    }

    private fun assignSubscribers(){
        handlerCurrentPlayerLiveData()
        handlerResultTextLiveData()
    }

    private fun handlerBtnSend(){
        textSentenceOfTale?.let { model.createSentenceOfTale(it) }
        assistantFragment.hideKeyboard()
        sentence_line.setText("")
        assistantFragment.makeInVisibleBtnSend()
        assistantFragment.showGameField()
        model.getCurrentPlayer()
    }

    private fun handlerCurrentPlayerLiveData(){
        model.subscribeOnCurrentPlayer().observe(viewLifecycleOwner, Observer {
            player_name.text=it.name
            resourceToUri(it.character.avatarUrl)?.let {uri->
                loadImage(uri, avatar)}
        })
    }

    private fun handlerResultTextLiveData(){
        model.subscribeOnResultText().observe(viewLifecycleOwner, Observer {
            story_body.text=it
        })
    }


    private fun navigateToGameEndScreen() {
        router.navigateTo(Screens.GameEndScreen())
    }

}