package ru.storytellers.viewmodels

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.*
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import ru.storytellers.model.datasource.resourcestorage.storage.CharacterStorage
import ru.storytellers.model.datasource.resourcestorage.storage.LocationStorage
import ru.storytellers.model.datasource.room.StoryDataSource
import ru.storytellers.model.entity.Story
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.model.repository.StoryRepository


@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(sdk = [28])
class StartViewModelTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `StartViewModel give correct Stories list from Story Repo`() {
        val storyTest1 = mockk<Story>()
        val storyTest2 = mockk<Story>()
        val listStory = listOf(storyTest1, storyTest2)

        val storyRepo = mockk<IStoryRepository>()
        every { storyRepo.getAll() } returns Single.just(listStory)

        val startViewModel = StartViewModel(storyRepo)
        startViewModel.getAllStory()

        Shadows.shadowOf(Looper.getMainLooper()).idle()

        val listStoryFromStoryRepo = startViewModel.subscribeOnSuccess().value?.data
        Assert.assertEquals(listStory, listStoryFromStoryRepo)
    }

}
