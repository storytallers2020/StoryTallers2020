package ru.storytellers.navigation

import android.net.Uri
import ru.storytellers.model.entity.SentenceOfTale
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.fragments.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screens {
    class StartScreen : SupportAppScreen(){
        override fun getFragment() = StartFragment.newInstance()
    }

    class CharacterCreateScreen : SupportAppScreen() {
        override fun getFragment() = CharacterCreateFragment.newInstance()
    }

    class LevelScreen : SupportAppScreen(){
        override fun getFragment() = LevelFragment()
    }

    class GameScreen : SupportAppScreen() {
        override fun getFragment() = GameFragment()
    }

    class GameStartScreen : SupportAppScreen() {
        override fun getFragment() = GameStartFragment.newInstance()
    }

    class GameEndScreen : SupportAppScreen() {
        override fun getFragment() = GameEndFragment.newInstance()
    }

    class LocationScreen : SupportAppScreen(){
        override fun getFragment() = LocationFragment.newInstance()
    }

    class RulesGameScreen : SupportAppScreen() {
        override fun getFragment() = RulesFragment.newInstance()
    }

    class SelectCoverScreen : SupportAppScreen() {
        override fun getFragment() = SelectCoverFragment.newInstance()
    }

    class TitleAndSaveStoryScreen : SupportAppScreen() {
        override fun getFragment() = TitleAndSaveStoryFragment.newInstance()
    }

    class LibraryScreen : SupportAppScreen() {
        override fun getFragment() = LibraryFragment.newInstance()
    }

    class BookReadingScreen(private val story: Story) : SupportAppScreen() {
        override fun getFragment() = LibraryBookReadingFragment.newInstance(story)
    }

    class BookEditingScreen(
        private val story: Story,
        private val sourceListSentences: List<SentenceOfTale>,
        private val titleStory: String,
        private val uriLocationImage: Uri
    ) : SupportAppScreen() {
        override fun getFragment() = LibraryBookEditingFragment.newInstance(
            story,
            sourceListSentences,
            titleStory,
            uriLocationImage
        )
    }

    class TeamCharacterScreen : SupportAppScreen() {
        override fun getFragment() = TeamCharacterFragment.newInstance()
    }

    class AboutDevelopersScreen : SupportAppScreen() {
        override fun getFragment() = AboutDevelopersFragment.newInstance()
    }

    class AboutAppScreen : SupportAppScreen() {
        override fun getFragment() = AboutAppFragment.newInstance()
    }

}