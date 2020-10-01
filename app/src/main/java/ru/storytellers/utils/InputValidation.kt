package ru.storytellers.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class InputValidation {

    private val onInputIncorrectLiveData = MutableLiveData<Int>()

    fun subscribeOnInputIncorrect(): LiveData<Int> {
        return onInputIncorrectLiveData
    }

     fun inputValidation(content:String): Int {
        val flagInputIsNormal = 0 // проходит валидацию
        val flagInputIsShort = 1 // входная строка короткая,меньще двух символов
        val flagInputIsGaps = 2 // входная строка состоит из одних пробелов
        if (content.length < 2) {
            setValueInputIncorrectLiveData(flagInputIsShort)
            return flagInputIsShort }
        if (content.stringNotContainSymbols()) {
            setValueInputIncorrectLiveData(flagInputIsGaps)
            return flagInputIsGaps
        }
         setValueInputIncorrectLiveData(flagInputIsNormal)
        return flagInputIsNormal
    }
    private fun setValueInputIncorrectLiveData(value:Int){
        onInputIncorrectLiveData.value=value

    }
}