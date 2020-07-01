package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_character_create.*
import org.koin.android.scope.currentScope
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.viewmodels.CreateCharacterViewModel

class CreateCharacterFragment: BaseFragment<DataModel>() {
    override lateinit var model: CreateCharacterViewModel
    override val layoutRes= R.layout.fragment_character_create

    companion object {
        fun newInstance() = CreateCharacterFragment()
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }

    override fun iniViewModel() {
        val viewModel: CreateCharacterViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun init() {
        btn_next.setOnClickListener {
            router.navigateTo(Screens.LocationlScreen())
        }
        back_button_character.setOnClickListener {backClicked()}
    }
}