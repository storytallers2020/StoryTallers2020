package ru.storytellers.engine.rules

class Rules() {

    private val rulesList = ArrayList<IRule>()

    fun addRule(rule: IRule) {
        rulesList.add(rule)
    }

    fun clearRules() {
        rulesList.clear()
    }

    fun isSentenceCorrect(str: String): Boolean {
        rulesList.forEach {
            if (!it.isCorrect(str)) return false
        }
        return true
    }
}