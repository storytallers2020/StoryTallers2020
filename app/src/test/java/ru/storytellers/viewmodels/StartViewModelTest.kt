package ru.storytellers.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.*
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.annotation.Config
import ru.storytellers.model.datasource.resourcestorage.storage.CharacterStorage
import ru.storytellers.model.datasource.resourcestorage.storage.LocationStorage
import ru.storytellers.model.datasource.room.StoryDataSource
import ru.storytellers.model.entity.Story
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.model.repository.IStoryRepository
import ru.storytellers.model.repository.StoryRepository


@RunWith(AndroidJUnit4::class)
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
    fun `try mockk it`() {
        val storyTest1 = mockk<Story>()
        val storyTest2 = mockk<Story>()
        val listStory = listOf(storyTest1, storyTest2)

        val storyRepo = mockk<IStoryRepository>()
        every { storyRepo.getAll() } returns Single.just(listStory)

        val startViewModel = StartViewModel(storyRepo)

        Assert.assertEquals(startViewModel.subscribeOnSuccess().value?.data?.size, storyRepo.getAll())

    }


//    @Test
//    fun `WTF`() {
//        val name: String = "name"
//        val imageUrl: String = "imageUrl"
//        val imageForRecycler: String = "image"
//        val descriptions: String = "description"
//
//        val authors: String = "author"
//        val coverUrl: String = "coverUrl"
//        val sentences: List<SentenceOfTale>? = emptyList()
//
//
//        val testLocation = Location(1, name, imageUrl, imageForRecycler, descriptions)
//        val testStory1 = Story(1, name, authors, coverUrl, testLocation, sentences)
//        val testStory2 = Story(2, name, authors, coverUrl, testLocation, sentences)
//
//        val listStory = listOf<Story>(testStory1, testStory2)
//        storyRepo.insertOrReplace(testStory1)
//        storyRepo.insertOrReplace(testStory2)
////        val storyRepo = mockk<IStoryRepository>()
////        every { storyRepo.getAll() } returns Single.just(listStory)
//        var observe:List<Story> = emptyList()
//        storyRepo.getAll().observeOn(AndroidSchedulers.mainThread()).subscribe({
//            observe=it
//        },{
//            it.printStackTrace()
//        })
//        Assert.assertEquals(observe, listStory)
//    }
}
