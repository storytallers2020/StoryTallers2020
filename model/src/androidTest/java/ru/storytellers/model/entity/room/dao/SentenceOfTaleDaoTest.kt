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
import ru.storytellers.model.entity.ContentTypeEnum
import ru.storytellers.model.entity.room.RoomSentenceOfTale
import ru.storytellers.model.entity.room.RoomStory
import ru.storytellers.model.entity.room.db.AppDatabase
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class SentenceOfTaleDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var sentenceDao: SentenceOfTaleDao
    private lateinit var storyDao: StoryDao

    private val contentType = ContentTypeEnum.TEXT
    private val content = "bla bla bla"
    private val testSentence = RoomSentenceOfTale(1, 1, 1, 1, content, contentType)
    private val listSentence = listOf(
        RoomSentenceOfTale(2, 2, 2, 2, content, contentType),
        RoomSentenceOfTale(3, 3, 3, 3, content, contentType),
        RoomSentenceOfTale(4, 4, 4, 4, content, contentType),
        RoomSentenceOfTale(5, 5, 5, 5, content, contentType)
    )

    private val testLocation: Long = 0
    private val testStory = RoomStory(1, "name0", "author0", "cover0", testLocation)
    private val listStories = listOf(
        RoomStory(2, "name1", "author1", "cover1", testLocation),
        RoomStory(3, "name2", "author2", "cover2", testLocation),
        RoomStory(4, "name3", "author3", "cover3", testLocation),
        RoomStory(5, "name4", "author4", "cover4", testLocation)
    )


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        sentenceDao = db.sentenceOfTaleDao
        storyDao = db.storyDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAllSentence() {
        for (story in listStories) {
            storyDao.insert(story)
        }
        sentenceDao.insertRange(listSentence)
        val listFromDatabase = sentenceDao.getAll()
        Assert.assertEquals(listFromDatabase, listSentence)
    }

    @Test
    fun getSentenceById() {
        sentenceDao.insert(testSentence)
        storyDao.insert(testStory)
        val sentenceFromDatabase = sentenceDao.getSentenceById(testSentence.id)
        Assert.assertEquals(testSentence, sentenceFromDatabase)
    }

    @Test
    fun insertSentence() {
        storyDao.insert(testStory)
        sentenceDao.insert(testSentence)
        val sentenceFromDatabase = sentenceDao.getSentenceById(testSentence.id)
        Assert.assertEquals(testSentence, sentenceFromDatabase)
    }

    @Test
    fun update() {
        storyDao.insert(testStory)
        sentenceDao.insert(testSentence)
        val updatedSentence = RoomSentenceOfTale(
            testSentence.id,
            testSentence.storyId,
            testSentence.playerId,
            testSentence.turn,
            "New Content",
            ContentTypeEnum.SOUND
        )

        sentenceDao.insert(updatedSentence)
        val sentenceFromDatabase = sentenceDao.getSentenceById(testSentence.id)
        Assert.assertEquals(updatedSentence, sentenceFromDatabase)
    }

    @Test
    fun deleteSentence() {
        storyDao.insert(testStory)
        sentenceDao.insert(testSentence)
        var sentenceFromDatabase = sentenceDao.getSentenceById(testSentence.id)
        Assert.assertEquals(testSentence, sentenceFromDatabase)

        sentenceDao.delete(testSentence)
        sentenceFromDatabase = sentenceDao.getSentenceById(testSentence.id)
        Assert.assertNotEquals(testSentence, sentenceFromDatabase)
    }

    @Test
    fun deleteListSentence() {
        for (story in listStories) {
            storyDao.insert(story)
        }
        sentenceDao.insertRange(listSentence)
        val listFromDatabase = sentenceDao.getAll()
        Assert.assertEquals(listFromDatabase, listSentence)

        val listForDelete = listOf(listSentence[0], listSentence[1])
        sentenceDao.delete(listForDelete)
        val notDeletedList = listOf(listSentence[2], listSentence[3])
        Assert.assertEquals(notDeletedList, sentenceDao.getAll())
    }

    @Test
    fun getAllByStoryId() {
        for (story in listStories) {
            storyDao.insert(story)
        }
        sentenceDao.insertRange(listSentence)

        val amountStory = sentenceDao.getAllStorySentence(listSentence[0].storyId)?.size
        Assert.assertEquals(1, amountStory)
    }
}