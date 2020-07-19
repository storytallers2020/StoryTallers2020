package ru.storytellers.ui.fragments

import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.viewmodels.LocationViewModel

class LocationFragment: BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_location
    override  val model: LocationViewModel by inject()


    companion object {
        fun newInstance() = LocationFragment()
    }

    override fun init() {
        iniViewModel()
        btn_next.setOnClickListener {
            router.navigateTo(Screens.GameScreen())
        }
        back_from_location.setOnClickListener {backClicked()}
    }

    override fun iniViewModel() {
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}