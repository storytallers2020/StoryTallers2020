package ru.storytellers.engine.showRules

import ru.storytellers.utils.collectSentence

class ShowAllSentencesRule: IShowRule {

    override fun getText(turn: Int, sentenceList: List<String>): String =
        sentenceList.collectSentence()

}