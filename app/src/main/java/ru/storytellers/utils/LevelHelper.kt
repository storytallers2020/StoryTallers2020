package ru.storytellers.utils

import ru.storytellers.application.StoryTallerApp
import ru.storytellers.engine.level.Level

fun getCurrentLevel(): Level? {
    val levelNum = StoryTallerApp.instance.gameStorage.getLevelGame()
    return StoryTallerApp.instance.levels.getLevelById(levelNum)
}