package ru.storytallers.ui.fragments

import androidx.lifecycle.Observer
import org.koin.android.scope.currentScope
import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.CreateCharacterViewModel
import ru.storytallers.viewmodels.StartViewModel
import ru.storytellers.model.DataModel

class CreateCharacterFragment: BaseFragment<DataModel>() {
    override lateinit var model: CreateCharacterViewModel
    override val layoutRes= R.layout.fragment_character_create

    companion object {
        fun newInstance() = CreateCharacterFragment()
    }

    override fun backClicked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iniViewModel() {
        val viewModel: CreateCharacterViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }
}