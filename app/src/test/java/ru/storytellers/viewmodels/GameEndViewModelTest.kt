package ru.storytellers.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config
import ru.storytellers.di.amplitudeModule
import ru.storytellers.di.gameEndModule
import ru.storytellers.di.gameEngine
import ru.storytellers.engine.GameStorage
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.SentenceOfTale

@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class GameEndViewModelTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val storage:GameStorage by inject()
    private val gameEndViewModel : GameEndViewModel by inject()

    @Before
    fun setUp() {
        loadKoinModules(
            listOf(gameEngine, amplitudeModule, gameEndModule)
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `GameEndViewModel url equals url in Location`() {
        val testUrl = "test_url"
        val location = mockk<Location>()
        every { location.imageUrl } returns testUrl
        storage.setLocationGame(location)
        gameEndViewModel.getUriBackgroundImage()
        val urlFromViewModel = gameEndViewModel.subscribeOnBackgroundImageUri().value.toString()
        Assert.assertEquals(testUrl, urlFromViewModel)
    }

    @Test
    fun `GameEndViewModel send correct textOfTale`(){
        val sentence1 = mockk<SentenceOfTale>()
        val sentence2 = mockk<SentenceOfTale>()

        val testContent1 = "testContent1"
        val testContent2 = "testContent2"

        storage.getSentences().add(sentence1)
        storage.getSentences().add(sentence2)

        every { sentence1.content } returns testContent1
        every { sentence2.content } returns testContent2

        every { sentence1.step } returns 1
        every { sentence2.step } returns 2

        gameEndViewModel.setTextOfStoryTaller()

        val textOfStory = gameEndViewModel.subscribeOnTextOfStoryTaller().value
        Assert.assertEquals((testContent1+testContent2), textOfStory)
    }
}
