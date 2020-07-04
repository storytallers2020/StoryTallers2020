package ru.storytellers.engine.level

class Levels() {

    private val rulesList = ArrayList<Level>()

    fun addLevel(level: Level) {
        rulesList.add(level)
    }

    fun clearRules() {
        rulesList.clear()
    }

}