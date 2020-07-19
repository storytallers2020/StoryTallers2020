package ru.storytellers.ui.fragments

import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_start.*
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.StartViewModel
import ru.storytellers.model.DataModel
import kotlinx.android.synthetic.main.fragment_start.new_tale_button
import org.koin.android.ext.android.inject
import ru.storytellers.navigation.Screens

class StartFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override  val model: StartViewModel by inject()
    private lateinit var startButton: MaterialButton
    private lateinit var rulesButton: TextView

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun init() {
        iniViewModel()
        rules_game_text_view.setOnClickListener { navigateToRulesGame() }
        startButton=new_tale_button
        startButton.setOnClickListener{ navigateToLevelScreen() }
    }

    private fun navigateToLevelScreen() {
        router.navigateTo(Screens.LevelScreen())
    }

    override fun iniViewModel() {
    }
    private fun navigateToRulesGame(){
        router.navigateTo(Screens.RulesGameScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}