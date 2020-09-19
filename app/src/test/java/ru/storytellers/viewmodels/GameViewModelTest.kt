package ru.storytellers.viewmodels

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import ru.storytellers.di.gameEngine
import ru.storytellers.engine.Game

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [28])
class GameViewModelTest():KoinTest{
    lateinit var game: Game
    lateinit var gameViewModel: GameViewModel


    @Before
    fun setUp() {
        loadKoinModules(gameEngine)
        game = mockk()
        gameViewModel = GameViewModel(game)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `something`(){

    }
}