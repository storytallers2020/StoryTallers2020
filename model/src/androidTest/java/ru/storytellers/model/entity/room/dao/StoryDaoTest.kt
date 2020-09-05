package ru.storytellers.model.entity.room.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.storytellers.model.entity.room.RoomStory
import ru.storytellers.model.entity.room.db.AppDatabase
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StoryDaoTest {
    private lateinit var storyDao: StoryDao
    private lateinit var db: AppDatabase
    private val testId: Long = 99
    private val testLocation: Long = 0
    private val storyForTests =
        RoomStory(testId, "Тестовая сказка", "Тестировщик", "Тестовая обложка", testLocation)
    private val listStories = listOf(
        RoomStory(1, "name1", "author1", "cover1", testLocation),
        RoomStory(2, "name2", "author2", "cover2", testLocation),
        RoomStory(3, "name3", "author3", "cover3", testLocation),
        RoomStory(4, "name4", "author4", "cover4", testLocation)
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        storyDao = db.storyDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAllStoryFromDatabase() {
        for (storyItem in listStories) {
            storyDao.insert(storyItem)
        }
        val list = storyDao.getAll()
        Assert.assertEquals(listStories, list)
    }

    @Test
    fun getStoryById() {
        storyDao.insert(storyForTests)
        val storyByIdFromDatabase = storyDao.getStoryById(testId)
        Assert.assertEquals(storyForTests, storyByIdFromDatabase)
    }


    @Test
    fun insertStory() {
        storyDao.insert(storyForTests)
        val storyFromDatabase = storyDao.getStoryById(testId)
        assert(storyForTests.equals(storyFromDatabase))
    }

    @Test
    fun updateStoryThroughInsert() {
        storyDao.insert(storyForTests)
        val update = "Тестировочная сказка"
        val storyUpdate =
            RoomStory(testId, update, storyForTests.authors, storyForTests.coverUrl, 0)
        storyDao.insert(storyUpdate)
        val storyFromDatabase = storyDao.getStoryById(testId)
        Assert.assertEquals(storyUpdate, storyFromDatabase)
    }

    @Test
    fun deleteStoryByIdFromDatabase() {
        storyDao.insert(storyForTests)
        val amountRecordNeedDelete = 1
        val amountRecordsWasDeleted = storyDao.deleteById(testId)
        val storyFromDatabase = storyDao.getStoryById(testId)
        Assert.assertNotEquals(storyForTests, storyFromDatabase)
        Assert.assertEquals(amountRecordNeedDelete, amountRecordsWasDeleted)
    }

    @Test
    fun deleteStoryFromDatabase() {
        storyDao.insert(storyForTests)
        storyDao.delete(storyForTests)
        val storyFromDatabase = storyDao.getStoryById(testId)
        Assert.assertNotEquals(storyForTests, storyFromDatabase)
    }

    @Test
    fun checkAppContext() {
        // Context of the app under test.
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        val testPackageName = "ru.storytellers.model.test"
        Assert.assertEquals(testPackageName, appContext.packageName)
    }
}