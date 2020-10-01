package ru.storytellers.model.datasource.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.entity.Location
import ru.storytellers.model.entity.room.RoomLocation
import ru.storytellers.model.entity.room.RoomStory
import ru.storytellers.model.entity.room.dao.LocationDao
import ru.storytellers.model.entity.room.dao.StoryDao
import ru.storytellers.model.entity.room.db.AppDatabase
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ILocationDataSourceTest {
    private lateinit var db: AppDatabase
    private lateinit var storyDao: StoryDao
    private lateinit var locationDao: LocationDao
    private val testString = "test"
    private val testLocation: Location = Location(1, testString, testString, testString, testString)
    private val testStory = RoomStory(1, "name0", "author0", "cover0", 0)
    private lateinit var locDateSource: ILocationDataSource
    private val roomLocation = RoomLocation(
        testLocation.id,
        testLocation.name,
        testLocation.imageUrl,
        testLocation.imageForRecycler,
        testLocation.descriptions
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        locationDao = db.locationDao
        storyDao = db.storyDao
        locDateSource = LocationDataSource(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testForLocationDao() {
        locationDao.insert(roomLocation)
        val roomLocFromDatabase = locationDao.getLocationById(roomLocation.id)
        Assert.assertEquals(roomLocation,roomLocFromDatabase)
    }

    @Test
    fun testSaveLocationInLocationDataSource(){
        locDateSource.insertOrReplace(testLocation)
        val roomLocFromDb = locDateSource.getLocationById(testLocation.id).blockingGet()
        val location = Location(roomLocFromDb.id, roomLocFromDb.name, roomLocFromDb.imageUrl, roomLocFromDb.imageForRecycler, roomLocFromDb.descriptions)
        Assert.assertEquals(testLocation,location)
    }
}