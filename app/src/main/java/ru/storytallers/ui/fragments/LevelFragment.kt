package ru.storytallers.ui.fragments

import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.CreateCharacterViewModel
import ru.storytallers.viewmodels.LevelViewModel
import ru.storytellers.model.DataModel

class LevelFragment: BaseFragment<DataModel>() {
    override lateinit var model: LevelViewModel
    override val layoutRes= R.layout.fragment_level
    companion object {
        fun newInstance() = LevelFragment()
    }
    override fun backClicked(): Boolean {
        TODO("Not yet implemented")
    }
}