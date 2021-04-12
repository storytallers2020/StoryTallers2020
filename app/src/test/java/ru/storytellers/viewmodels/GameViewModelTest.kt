package ru.storytellers.viewmodels

import android.os.Looper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.di.amplitudeModule
import ru.storytellers.di.gameEngine
import ru.storytellers.engine.Game
import ru.storytellers.engine.GameStorage
import ru.storytellers.engine.level.Level
import ru.storytellers.engine.rules.Rules
import ru.storytellers.engine.showRules.ShowAllSentencesRule
import ru.storytellers.engine.wordRules.RandomWordRule
import ru.storytellers.model.datasource.storage.WordStorage
import ru.storytellers.model.entity.Character
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [28])
class GameViewModelTest() : KoinTest {
    lateinit var game: Game
    lateinit var gameViewModel: GameViewModel
    lateinit var storage: GameStorage


    @Before
    fun setUp() {
        loadKoinModules(listOf(gameEngine, amplitudeModule))
        storage = StoryHeroesApp.instance.gameStorage
        val storage = WordStorage()
        game = Game(storage)
        gameViewModel = GameViewModel(game, storage)

        val testCharacter1 = Character(1, "CharName", "AvatarUrl", "AvatarUrlSelected?")
        val testCharacter2 = Character(2, "CharName", "AvatarUrl", "AvatarUrlSelected?")
        val testPlayer1 = Player(1, "TestPlayer", testCharacter1)
        val testPlayer2 = Player(2, "TestPlayer", testCharacter2)
        val listPlayer = listOf(testPlayer1, testPlayer2)
        val level = Level(
            1,
            Rules(),
            ShowAllSentencesRule(),
            RandomWordRule(false)
        )
        game.newGame(listPlayer, level)

    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should be same Url in storage and GameViewModel `() {

        val testLocation = mockk<Location>()
        every { testLocation.imageUrl } returns "testUrl"

        storage.setLocationGame(testLocation)
        val testImageUrl = storage.location?.imageUrl

        gameViewModel.getUriBackgroundImage()

        Shadows.shadowOf(Looper.getMainLooper()).idle()
        val imageUrlFromGameViewModel =
            gameViewModel.subscribeOnBackgroundImageChanged().value.toString()
        Assert.assertEquals(testImageUrl, imageUrlFromGameViewModel)
    }

    @Test
    fun `GameViewModel calls game_nextStep`() {
        val testContent = "very long string"
        gameViewModel.onButtonSendClicked(testContent)
        val testStringIsGood = gameViewModel.subscribeOnSentenceChecked().value
        if (testStringIsGood != null) {
            Assert.assertTrue(testStringIsGood)
        }
    }
}