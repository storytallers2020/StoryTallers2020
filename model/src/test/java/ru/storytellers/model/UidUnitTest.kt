package ru.storytellers.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.storytellers.utils.fieldsToLogString
import ru.storytellers.utils.getUid

class UidUnitTest {

    @Test
    fun getUidTest() {

        val longNum = getUid()
        val longNum2 = getUid()

        Assert.assertNotEquals(longNum, longNum2)
    }
}