package ru.storytellers.viewmodels

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import ru.storytellers.application.StoryHeroesApp
import ru.storytellers.di.gameEngine
import ru.storytellers.model.entity.Cover
import ru.storytellers.model.repository.ICoverRepository


@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [28])
class SelectCoverViewModelTest() : KoinTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var coverRepo = mockk<ICoverRepository>()
    private var selectCoverViewModel = SelectCoverViewModel(coverRepo)
    lateinit var cover1: Cover
    lateinit var cover2: Cover
    lateinit var listCover: List<Cover>


    @Before
    fun setUp() {
        cover1 = Cover(1, "1", "1", "1")
        cover2 = Cover(2, "2", "2", "2")
        listCover = listOf(cover1, cover2)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `get all Cover from SelectCoverViewModel`() {

        every { coverRepo.getAll() } returns Single.just(listCover)

        selectCoverViewModel.getAllCover()

        //прямо здесь выполняются все задачи, которые нужно отправить в основной поток
        shadowOf(Looper.getMainLooper()).idle()
        val listCoverFromCoverRepo = selectCoverViewModel.subscribeOnSuccess().value?.data
        assertEquals(listCover, listCoverFromCoverRepo)
    }

    @Test
    fun `try set Cover`() {
        loadKoinModules(gameEngine)
        selectCoverViewModel.setCoverStory(cover1)
        val coverFromGameStorage = StoryHeroesApp.instance.gameStorage.getCoverStory()
        assertEquals(cover1, coverFromGameStorage)
    }

}
