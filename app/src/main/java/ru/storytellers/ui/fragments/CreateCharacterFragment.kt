package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.CreateCharacterViewModel
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