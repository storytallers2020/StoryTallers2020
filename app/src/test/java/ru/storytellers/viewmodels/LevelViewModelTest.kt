package ru.storytellers.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config
import ru.storytellers.di.*


@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class LevelViewModelTest : KoinTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val levelViewModel: LevelViewModel by inject()


    @Before
    fun setUp() {
        loadKoinModules(
            listOf(levelModel, gameEngine)
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `on start level id should be 0`() {
        val startLevelId = levelViewModel.getLevelGame()
        Assert.assertEquals(0, startLevelId)
    }

    @Test
    fun `level id has been changed`() {
        val testId = 1
        levelViewModel.setLevelGame(testId)
        val levelId = levelViewModel.subscribeOnChangeLevel().value
        Assert.assertEquals(testId, levelId)
    }

    @Test
    fun `On start player list is empty`() {
        Assert.assertFalse(levelViewModel.isPlayerListNotEmpty())
    }
}