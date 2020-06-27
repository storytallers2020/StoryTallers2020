package ru.storytallers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.inject
import ru.storytallers.R
import ru.storytallers.di.injectDependencies
import ru.storytallers.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    lateinit var navigatorHolder: NavigatorHolder
    val navigator = SupportAppNavigator(this, R.id.container)
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        injection()
        router.replaceScreen(Screens.StartScreen())
    }

    private fun injection(){
        injectDependencies()
        val rout: Router by inject()
        router=rout
        val navigHold: NavigatorHolder by inject()
        navigatorHolder=navigHold
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }
    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
