package ru.storytellers.ui

import android.net.Uri
import android.view.View

interface BackgroundListener {
    var backgroundView: View
    fun setBackground(uri: Uri)
    fun setDefaultBackground()
}