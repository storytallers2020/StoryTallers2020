package ru.storytellers.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config
import ru.storytellers.di.gameEngine
import ru.storytellers.engine.Game
import ru.storytellers.engine.GameStorage
import ru.storytellers.engine.level.Level
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.Player

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class GameStartViewModelTest: KoinTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var game:Game
    private lateinit var gameStartViewModel: GameStartViewModel
    private val storage: GameStorage by inject()

    @Before
    fun setUp() {
        loadKoinModules(gameEngine)
        game = mockk()
        gameStartViewModel = GameStartViewModel(game)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `take GameLevel from storage`(){
        val testLevel = mockk<Level>()
        every { testLevel.id } returns 33
        storage.level= testLevel
        gameStartViewModel.requestGameLevelFromStorage()
        val currentLevelId = gameStartViewModel.subscribeOnLevelGame().value

        assertEquals(testLevel.id, currentLevelId)
    }

    @Test
    fun `create new game`(){
        val player1 = mockk<Player>()
        val player2 = mockk<Player>()
        storage.getPlayers().add(player1)
        storage.getPlayers().add(player2)

        val testLevel = mockk<Level>()
        storage.level= testLevel
        val listOfPlayer = storage.getPlayers()

        every { game.newGame(listOfPlayer, testLevel) } returns Unit

        gameStartViewModel.createNewGame()
        verify { game.newGame(listOfPlayer,testLevel) }
    }

    @Test
    fun `get UriBackgroundImage from storage`(){
        val testUrl = "test_url"
        val testLocation = mockk<Location>()
        every { testLocation.imageUrl } returns testUrl
        storage.setLocationGame(testLocation)
        gameStartViewModel.getUriBackgroundImage()
        val urlFromStorage = gameStartViewModel.subscribeOnBackgroundImageChanged().value.toString()
        assertEquals(testUrl, urlFromStorage)
    }
}