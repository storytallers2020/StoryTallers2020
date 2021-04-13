package ru.storytellers.engine

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.storytellers.engine.rules.IRule
import ru.storytellers.engine.rules.OneSentenceInTextRule

class RulesUnitTest {

    private val str1 = "Предложение 1."
    private val str2 = "Предложение 1. Предолжение 2."
    private val str3 = "Предложение 1... так бывает"
    private val str4 = "Предложение 1... так нельзя."
    private val str5 = ""

    private lateinit var rule: IRule

    @Before
    fun before() {
        rule = OneSentenceInTextRule()
    }
    @Test
    fun `One dot in sentence Test`() {
        val res = rule.isCorrect(str1)
        Assert.assertTrue(res)
    }

    @Test
    fun `Two dots should return false Test`() {
        val res = rule.isCorrect(str2)
        Assert.assertFalse(res)
    }

    @Test
    fun `Three dots together should return true Test`() {
        val res = rule.isCorrect(str3)
        Assert.assertTrue(res)
    }

    @Test
    fun `Three dots together and one not should return false Test`() {
        val res = rule.isCorrect(str4)
        Assert.assertFalse(res)
    }

    @Test
    fun `Empty sentence should return trueTest`() {
        val res = rule.isCorrect(str5)
        Assert.assertTrue(res)
    }

}