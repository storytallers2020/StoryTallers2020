package ru.storytellers.utils

import android.content.Context
import androidx.core.content.ContextCompat
import ru.storytellers.R

class ResourceProviderLevelFragment(private val context: Context?) {

    //получение цветов из ресурсов
    fun getColorWhite() =
        context?.let { ContextCompat.getColor(it, R.color.white) }

      fun getColorYellow() =
        context?.let { ContextCompat.getColor(it, R.color.yellow) }

    // получение описания уровней из ресурсов
     fun getTextDescriptionMediumLvl() =
        context?.resources?.getString(R.string.medium_description)
     fun getTextDescriptionEasyLvl() =
        context?.resources?.getString(R.string.easy_description)
     fun getTextDescriptionHardLvl() = context?.resources?.getString(R.string.hard_description)
}