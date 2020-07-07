package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game_end.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.GameEndViewModel

class GameEndFragment: BaseFragment<DataModel>() {
    override lateinit var model: GameEndViewModel
    override val layoutRes = R.layout.fragment_game_end

    companion object {
        fun newInstance() = GameEndFragment()
    }

    override fun iniViewModel() {
        val viewModel: GameEndViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
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