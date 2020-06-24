package ru.storytallers.ui.fragments

import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.CreateCharacterViewModel
import ru.storytellers.model.DataModel

class CreateCharacterFragment: BaseFragment<DataModel>() {
    override lateinit var model: CreateCharacterViewModel
    override val layoutRes= R.layout.fragment_character_create
    companion object {
        fun newInstance() = CreateCharacterFragment()
    }
}