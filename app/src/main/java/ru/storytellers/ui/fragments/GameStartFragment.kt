package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game_start.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.GameStartViewModel

class GameStartFragment : BaseFragment<DataModel>() {
    override val model: GameStartViewModel by inject()
    override val layoutRes = R.layout.fragment_game_start

    companion object {
        fun newInstance() = GameStartFragment()
    }

    override fun init() {
        iniViewModel()
        back_button_location.setOnClickListener { backClicked() }
    }

    override fun iniViewModel() {
        btn_next.setOnClickListener { navigateToGameScreen() }
    }

    override fun onStart() {
        super.onStart()
        model.getLevelGame()
    }

    override fun onResume() {
        super.onResume()
        model.subscribeOnLevelGame().observe(viewLifecycleOwner, Observer {
            when (it) {
                0 -> {
                    getRuleFromResources(R.string.easy_description)
                }
                1 -> {
                    getRuleFromResources(R.string.medium_description)
                }
                else -> {
                    getRuleFromResources(R.string.hard_description)
                }
            }
        })
    }

    private fun getRuleFromResources(stringId: Int?) {
        var rule = stringId?.let { context?.getString(it) }
        setRule(rule)
    }

    private fun setRule(rule: String?) {
        rule?.let { text_rule_tv.text = it }
    }

    private fun navigateToGameScreen() {
        router.navigateTo(Screens.GameScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}