package ru.storytallers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_level.*
import org.koin.android.scope.currentScope
import ru.storytallers.R
import ru.storytallers.navigation.Screens
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.LevelViewModel
import ru.storytellers.model.DataModel

class LevelFragment: BaseFragment<DataModel>() {
    override lateinit var model: LevelViewModel
    override val layoutRes= R.layout.fragment_level
    companion object {
        fun newInstance() = LevelFragment()
    }

    override fun backClicked(): Boolean {
        router.backTo(Screens.StartScreen())
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button.setOnClickListener { backClicked() }
    }

    override fun iniViewModel() {
        val viewModel: LevelViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }
}