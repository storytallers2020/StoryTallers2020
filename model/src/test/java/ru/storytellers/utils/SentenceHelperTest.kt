package ru.storytellers.utils

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.storytellers.engine.rules.IRule
import ru.storytellers.engine.rules.OneSentenceInTextRule

class SentenceHelperTest {

    @Test
    fun `trim space left and right Test`() {
        val text = "   My text.  "
        val expectedText = "My text."

        val trimmedText = text.trimSentenceSpace()

        Assert.assertEquals(expectedText, trimmedText)
    }

    @Test
    fun `last symbol is dot Test`() {
        val text = "My text."

        val res = text.isLastPunctuationSymbol()

        Assert.assertEquals(true, res)
    }

    @Test
    fun `last symbol is question mark Test`() {
        val text = "My text?"

        val res = text.isLastPunctuationSymbol()

        Assert.assertEquals(true, res)
    }

    @Test
    fun `last symbol is not dot Test`() {
        val text = "My text:"

        val res = text.isLastPunctuationSymbol()

        Assert.assertEquals(false, res)
    }

    @Test
    fun `addDotIfNeed Test`() {
        val text = "My text"
        val expectedText = "My text."
        val res = text.addDotIfNeed()

        Assert.assertEquals(expectedText, res)
    }

    @Test
    fun `collectSentence Test`() {
        val textList = listOf<String>(
            "text1.",
            "text2.",
            "text3."
        )
        val text = textList.collectSentence()
        val expectedText = "text1.\r\ntext2.\r\ntext3."

        Assert.assertEquals(expectedText, text)
    }

    @Test
    fun `addSpaceAfterPunctuationSymbol Test`() {
        val text = "text1.text2.text3."
        val expectedText = "text1. text2. text3. "

        val res = text.addSpaceAfterPunctuationSymbol()

        Assert.assertEquals(expectedText, res)
    }

}