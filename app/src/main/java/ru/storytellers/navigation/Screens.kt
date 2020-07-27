package ru.storytellers.navigation

import ru.storytellers.ui.fragments.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens {
    class StartScreen : SupportAppScreen(){
        override fun getFragment() = StartFragment.newInstance()
    }

    class CreateCharacterScreen() : SupportAppScreen(){
        override fun getFragment() = CreateCharacterFragment.newInstance()
    }

    class LevelScreen : SupportAppScreen(){
        override fun getFragment() = LevelFragment.newInstance()
    }

    class GameScreen : SupportAppScreen() {
        override fun getFragment() = GameFragment.newInstance()
    }

    class GameStartScreen : SupportAppScreen() {
        override fun getFragment() = GameStartFragment.newInstance()
    }

    class GameEndScreen(private val textResultStoryTaller:String) : SupportAppScreen() {
        override fun getFragment() = GameEndFragment.newInstance(textResultStoryTaller)
    }

    class LocationScreen : SupportAppScreen(){
        override fun getFragment() = LocationFragment.newInstance()
    }
    class RulesGameScreen : SupportAppScreen() {
        override fun getFragment() = RulesGame.newInstance()
    }
    class SelectCoverScreen : SupportAppScreen() {
        override fun getFragment() = SelectCoverFragment.newInstance()
    }


}