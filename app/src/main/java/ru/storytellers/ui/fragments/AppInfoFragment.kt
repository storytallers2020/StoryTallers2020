package ru.storytellers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_start_about.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.BackButtonListener
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class AppInfoFragment : Fragment(), BackButtonListener {
    private lateinit var navigatorHolder: NavigatorHolder
    private lateinit var router: Router

    companion object {
        fun newInstance() = AppInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectRouter()
        return inflater.inflate(R.layout.fragment_start_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         back_button_about.setOnClickListener { backClicked() }
    }

    private fun injectRouter() {
        val navigHold: NavigatorHolder by inject()
        navigatorHolder = navigHold
        val rout: Router by inject()
        router = rout
    }

    override fun backClicked(): Boolean {
        router.replaceScreen(Screens.StartScreen())
        return true
    }
}