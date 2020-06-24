package ru.storytallers.ui.fragments

import ru.storytallers.R
import ru.storytallers.ui.fragments.basefragment.BaseFragment
import ru.storytallers.viewmodels.AuthViewModel
import ru.storytellers.model.DataModel

class AuthFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_auth
    override lateinit var model: AuthViewModel
    companion object {
        fun newInstance() = AuthFragment()
    }
}