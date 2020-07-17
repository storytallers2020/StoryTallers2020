package ru.storytellers.ui.fragments

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.GameViewModel

class GameFragment: BaseFragment<DataModel>() {
    override lateinit var model: GameViewModel
    override val layoutRes = R.layout.fragment_game

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun iniViewModel() {
        val viewModel: GameViewModel by inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun init() {
        showHintAndHideGameScreen()
        setEndClickListener()
    }

    private fun setEndClickListener() {
        tv_end.setOnClickListener {
            router.navigateTo(Screens.GameEndScreen())
        }
    }

    private fun showHintAndHideGameScreen() {
        tv_hint.visibility = View.VISIBLE

        layout_tale.visibility = View.GONE
        layout_step_by.visibility = View.GONE
        tv_end.visibility = View.GONE
        et_step.visibility = View.GONE

        layout_main.setOnClickListener {
            showGameScreenAndHideHint()
        }
    }

    private fun showGameScreenAndHideHint() {
        tv_hint.visibility = View.GONE

        layout_tale.visibility = View.VISIBLE
        layout_step_by.visibility = View.VISIBLE
        tv_end.visibility = View.VISIBLE
        et_step.visibility = View.VISIBLE

        layout_main.setOnClickListener(null)
    }


}