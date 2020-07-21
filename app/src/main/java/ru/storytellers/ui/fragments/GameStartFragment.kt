package ru.storytellers.ui.fragments

import kotlinx.android.synthetic.main.fragment_game_start.*
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.baseviewmodel.BaseViewModel

class GameStartFragment: BaseFragment<DataModel>() {
    override val model: BaseViewModel<DataModel>
        get() = TODO("Not yet implemented")
    override val layoutRes=R.layout.fragment_game_start
    companion object {
        fun newInstance() = GameStartFragment()
    }

    override fun init() {
        btn_next.setOnClickListener { navigateToGameScreen() }
        back_button_location.setOnClickListener { backClicked() }
    }

    override fun iniViewModel() {
        TODO("Not yet implemented")
    }

    private fun navigateToGameScreen() {
        router.navigateTo(Screens.GameScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}