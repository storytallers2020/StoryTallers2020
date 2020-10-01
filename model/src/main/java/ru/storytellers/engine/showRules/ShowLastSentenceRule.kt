package ru.storytellers.engine.showRules

import ru.storytellers.utils.getPrevSentences

class ShowLastSentenceRule: IShowRule {

    override fun getText(turn: Int, sentenceList: List<String>): String =
        sentenceList.getPrevSentences(turn)

}