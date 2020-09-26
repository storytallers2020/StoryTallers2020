package ru.storytellers.utils

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


fun hideSoftKey(v: View) {
    (v.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun showSoftKey(v: View) {
    (v.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        showSoftInput(v, 0)
    }
}
