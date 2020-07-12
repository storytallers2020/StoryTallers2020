package ru.storytellers.navigation

import ru.storytellers.ui.fragments.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens {
    class StartScreen : SupportAppScreen(){
        override fun getFragment() = StartFragment.newInstance()
    }

    class CreateCharacterScreen( private val levelGame:Int) : SupportAppScreen(){
        override fun getFragment() = CreateCharacterFragment.newInstance(levelGame)
    }

    class LevelScreen : SupportAppScreen(){
        override fun getFragment() = LevelFragment.newInstance()
    }

    class GameScreen : SupportAppScreen() {
        override fun getFragment() = GameFragment.newInstance()
    }

    class GameEndScreen : SupportAppScreen() {
        override fun getFragment() = GameEndFragment.newInstance()
    }

    class LocationScreen : SupportAppScreen(){
        override fun getFragment() = LocationFragment.newInstance()
    }
    class RulesGameScreen : SupportAppScreen() {
        override fun getFragment() = RulesGame.newInstance()
    }
}