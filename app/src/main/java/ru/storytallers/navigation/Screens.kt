package ru.storytallers.navigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens {
    class AuthScreen : SupportAppScreen(){
        override fun getFragment(): Fragment {
            return super.getFragment()
        }
    }
}