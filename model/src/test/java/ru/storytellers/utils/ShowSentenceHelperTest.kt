package ru.storytellers.utils

import org.junit.Assert
import org.junit.Test

class ShowSentenceHelperTest {

    @Test
    fun `collectSentence Test`() {
        val textList = listOf(
            "text1.",
            "- text2.",
            "text3.",
            "text4."
        )
        val text = textList.collectSentence()
        val expectedText = "text1. \r\n- text2.\r\ntext3. text4. "

        Assert.assertEquals(expectedText, text)
    }

    @Test
    fun `addSpaceAfterPunctuationSymbol Test`() {
        val text = "text1.text2?text3!"
        val expectedText = "text1. text2? text3! "

        val res = text.addSpaceAfterPunctuationSymbol()

        Assert.assertEquals(expectedText, res)
    }

    @Test
    fun `isStartFromDash Test`() {
        val text = "-text1."
        val res = text.isStartFromDash()

        Assert.assertEquals(true, res)
    }

    @Test
    fun `is not Start From Dash Test`() {
        val text = "text1."
        val res = text.isStartFromDash()

        Assert.assertEquals(false, res)
    }

    @Test
    fun `isDashSymbol Test`() {
        val dash = '-'
        val res = dash.isDashSymbol()

        Assert.assertEquals(true, res)
    }

    @Test
    fun `is not Dash Symbol Test`() {
        val dash = '0'
        val res = dash.isDashSymbol()

        Assert.assertEquals(false, res)
    }

}