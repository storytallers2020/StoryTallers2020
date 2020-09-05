package ru.storytellers.model.entity.room.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        storyDao = db.storyDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun saveStoryToDatabase() {
        val story = RoomStory(99,"Тестовая сказка", "Тестировщик", "Тестовая обложка", 0)
        storyDao.insert(story)
        val storyFromDatabase = storyDao.getStoryById(99)
        assert(story.equals(storyFromDatabase))
    }

    @Test
    fun deleteStoryFromDatabase(){
        val story = RoomStory(99,"Тестовая сказка", "Тестировщик", "Тестовая обложка", 0)
        storyDao.insert(story)
        storyDao.deleteById(99)
        var storyFromDatabase = storyDao.getStoryById(99)
        Assert.assertNotEquals(story, storyFromDatabase)

        storyDao.insert(story)
        storyDao.deleteById(98)
        storyFromDatabase = storyDao.getStoryById(99)
        Assert.assertEquals(story, storyFromDatabase)

        storyDao.delete(story)
        storyFromDatabase = storyDao.getStoryById(99)
        Assert.assertNotEquals(story, storyFromDatabase)
    }

//    @Test
//    fun updateStory(){
//        val story = RoomStory(99,"Тестовая сказка", "Тестировщик", "Тестовая обложка", 0)
//        storyDao.insert(story)
//    }

    @Test
    fun checkAppContext() {
        // Context of the app under test.
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        Assert.assertEquals(
            "ru.storytellers.model.test",
            appContext.packageName
        )
    }
}