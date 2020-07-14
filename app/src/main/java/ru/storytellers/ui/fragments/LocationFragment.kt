package ru.storytellers.ui.fragments

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_location.*
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.model.DataModel
import org.koin.android.scope.currentScope
import ru.storytellers.navigation.Screens
import ru.storytellers.viewmodels.LocationViewModel


class LocationFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_location
    override lateinit var model: LocationViewModel


    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun init() {
        iniViewModel()
        btn_next.setOnClickListener {
            router.navigateTo(Screens.GameScreen())
        }
    }

    override fun iniViewModel() {
        val viewModel: LocationViewModel by currentScope.inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer<DataModel> {
        } )
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}