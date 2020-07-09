package ru.storytellers.utils

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.storytellers.engine.rules.IRule
import ru.storytellers.engine.rules.OneSentenceInTextRule

class GameHelperTest {

    @Test
    fun `game turn 1 Test`() {
        val turn = 1
        val playersCount = 5

        val index = getPlayerNumByTurn(turn, playersCount)

        Assert.assertEquals(1, index)
    }

    @Test
    fun `game turn less than players Test`() {
        val turn = 2
        val playersCount = 5

        val index = getPlayerNumByTurn(turn, playersCount)

        Assert.assertEquals(2, index)
    }

    @Test
    fun `game turn rather more than players Test`() {
        val turn = 5
        val playersCount = 3

        val index = getPlayerNumByTurn(turn, playersCount)

        Assert.assertEquals(2, index)
    }

    @Test
    fun `game turn much more than players Test`() {
        val turn = 14
        val playersCount = 3

        val index = getPlayerNumByTurn(turn, playersCount)

        Assert.assertEquals(2, index)
    }

}