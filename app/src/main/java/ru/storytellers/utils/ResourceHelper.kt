package ru.storytellers.utils

import android.content.Context
import androidx.core.content.ContextCompat
import ru.storytellers.R

class ResourceHelper(private val context: Context?) {

    val LEVEL_GAME_EASY = 0
    val LEVEL_GAME_MEDIUM = 1
    val LEVEL_GAME_HARD = 2

    //получение цветов из ресурсов
    fun getColorWhite() =
        context?.let { ContextCompat.getColor(it, R.color.white) } ?: 0xFFFFFF

      fun getColorYellow() =
        context?.let { ContextCompat.getColor(it, R.color.yellow) } ?: 0xF9BE40

    // получение описания уровней из ресурсов
    fun getLevelDescription(levelId: Int): String =
        when (levelId) {
            LEVEL_GAME_HARD -> {
                context?.resources?.getString(R.string.rules_hard_description) ?: ""
            }
            LEVEL_GAME_MEDIUM -> {
                context?.resources?.getString(R.string.rules_medium_description) ?: ""
            }
            LEVEL_GAME_EASY -> {
                context?.resources?.getString(R.string.rules_easy_description) ?: ""
            }
            else -> {
                context?.resources?.getString(R.string.rules_easy_description) ?: ""
            }
        }

}