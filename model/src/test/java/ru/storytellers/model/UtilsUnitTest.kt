package ru.storytellers.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.storytellers.utils.fieldsToLogString

class UtilsUnitTest {
    class Data(val id: Int, val name: String)
    private lateinit var data: Data

    @Before
    fun before() {
        data = Data(1, "TestName")
    }

    @Test
    fun fieldsToLogStringTest() {

        val logString = data.fieldsToLogString()
        val expectedString = "id: [1], name: [TestName], "

        Assert.assertEquals(expectedString, logString)
    }
}