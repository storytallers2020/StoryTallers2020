package ru.storytellers.navigation

import ru.storytellers.ui.fragments.StartFragment
import ru.storytellers.ui.fragments.CreateCharacterFragment
import ru.storytellers.ui.fragments.LevelFragment
import ru.storytellers.ui.fragments.LocationFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens {
    class StartScreen : SupportAppScreen(){
        override fun getFragment()= StartFragment.newInstance()
    }
    class CreateCharacterScreen : SupportAppScreen(){
        override fun getFragment()= CreateCharacterFragment.newInstance()
    }

    class LevelScreen : SupportAppScreen(){
        override fun getFragment()= LevelFragment.newInstance()
    }
    class LocationlScreen : SupportAppScreen(){
        override fun getFragment()= LocationFragment.newInstance()
    }
}