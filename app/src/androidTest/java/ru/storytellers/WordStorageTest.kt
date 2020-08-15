package ru.storytellers


import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.storytellers.model.datasource.resourcestorage.storage.WordStorage

@RunWith(AndroidJUnit4::class)
class WordStorageTest {

    private lateinit var context: Context
    private lateinit var storage: WordStorage

    @Before
    fun init() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        storage = WordStorage(context)
    }

    @Test
    fun useAppContext() {
        Assert.assertEquals("ru.storytellers", context.packageName)
    }

    @Test
    fun testSeparateString()  {
        val count = storage.getAll().count()

        Assert.assertTrue(count > 1)
    }

    @Test
    fun testRandomWord() {
        val word = storage.getRandomWord()

        assert(word.isNotEmpty())
    }
}