package ru.storytellers.utils

interface DialogCaller {
    fun onDialogPositiveButton(tag: String?)
    fun onDialogNegativeButton(tag: String?)
}