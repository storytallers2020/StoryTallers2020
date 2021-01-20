package ru.storytellers

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import ru.storytellers.model.datasource.storage.WordStorage

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.storytellers", appContext.packageName)
    }

    @Test
    fun testSeparateString()  {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val storage = WordStorage(appContext)
        val count = storage.getAll().count()

        Assert.assertTrue(count > 1)
    }
}
