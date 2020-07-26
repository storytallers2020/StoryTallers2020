package ru.storytellers.utils

import org.junit.Assert
import org.junit.Test

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
    fun `there are dot in the end of sentence Test`() {
        val text = "My text."
        val expectedText = "My text."
        val res = text.addDotIfNeed()

        Assert.assertEquals(expectedText, res)
    }

}