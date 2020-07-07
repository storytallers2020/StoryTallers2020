package ru.storytellers.navigation

import androidx.fragment.app.Fragment
import ru.storytellers.ui.fragments.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens {
    class StartScreen : SupportAppScreen(){
        override fun getFragment() = StartFragment.newInstance()
    }

    class CreateCharacterScreen : SupportAppScreen(){
        override fun getFragment() = CreateCharacterFragment.newInstance()
    }

    class LevelScreen : SupportAppScreen(){
        override fun getFragment() = LevelFragment.newInstance()
    }

    class GameScreen : SupportAppScreen() {
        override fun getFragment() = GameFragment.newInstance()
    }

    class LocationlScreen : SupportAppScreen(){
        override fun getFragment() = LocationFragment.newInstance()
    }
    class RulesGameScreen : SupportAppScreen() {
        override fun getFragment() = RulesGame.newInstance()
    }
}