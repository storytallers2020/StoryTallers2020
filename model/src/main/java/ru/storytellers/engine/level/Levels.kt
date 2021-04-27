package ru.storytellers.engine.level

class Levels {

    private val levelList = ArrayList<Level>()

    fun getLevelById(id: Int): Level =
        levelList.first { it.id == id }

    fun addLevel(level: Level) {
        levelList.add(level)
    }

    fun clearLevels() {
        levelList.clear()
    }

}