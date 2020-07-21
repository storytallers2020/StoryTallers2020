package ru.storytellers.ui.fragments

import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.Game
import ru.storytellers.engine.level.Level
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.Player
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.assistant.GameFragmentAssistant
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.GameViewModel

class GameFragment: BaseFragment<DataModel>() {
    private val game: Game by inject()
    private val level: Level by inject()
    private lateinit var assistantFragment: GameFragmentAssistant
    private  val listPlayer= StoryTallerApp.instance.gameStorage.getListPlayers()
    override val model: GameViewModel by inject()
    override val layoutRes = R.layout.fragment_game

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
        assistantFragment=GameFragmentAssistant(this)
        showHintAndHideGameScreen()
        showIntro()
        setEndClickListener()
    }



    private fun setEndClickListener() {
//        button_end.setOnClickListener {
//            router.navigateTo(Screens.GameEndScreen())
//        }
    }

    private fun showIntro() {
//        reminder_intro.visibility = View.VISIBLE
//        game_field.visibility = View.GONE

    }

    private fun showGameField() {
//        reminder_intro.visibility = View.GONE
//        game_field.visibility = View.VISIBLE
    }


}