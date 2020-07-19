package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game_end.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.GameEndViewModel

class GameEndFragment: BaseFragment<DataModel>() {
    override  val model: GameEndViewModel by inject()
    override val layoutRes = R.layout.fragment_game_end

    companion object {
        fun newInstance() = GameEndFragment()
    }

    override fun iniViewModel() {
    }

    override fun init() {
        setResumeClickListener()
    }

    fun setResumeClickListener() {
        tv_resume.setOnClickListener {
            router.navigateTo(Screens.GameScreen())
        }
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}