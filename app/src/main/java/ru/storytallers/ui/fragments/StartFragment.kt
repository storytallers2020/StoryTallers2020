package ru.storytallers.ui.fragments

import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.AuthViewModel
import ru.storytellers.model.DataModel

class StartFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override lateinit var model: AuthViewModel
    companion object {
        fun newInstance() = StartFragment()
    }
}