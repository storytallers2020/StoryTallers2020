package ru.storytallers.navigation

import ru.storytallers.ui.fragments.AuthFragment
import ru.storytallers.ui.fragments.CreateCharacterFragment
import ru.storytallers.ui.fragments.LevelFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens {
    class AuthScreen : SupportAppScreen(){
        override fun getFragment()= AuthFragment.newInstance()
    }
    class CreateCharacterScreen : SupportAppScreen(){
        override fun getFragment()= CreateCharacterFragment.newInstance()
    }

    class LevelScreen : SupportAppScreen(){
        override fun getFragment()= LevelFragment.newInstance()
    }
}