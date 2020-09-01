package ru.storytellers.utils

import android.content.Context
import android.view.View
import androidx.appcompat.widget.PopupMenu

class CustomPopupMenu(context: Context, view: View, resInt: Int) : PopupMenu(context,  view) {
    private val res = resInt

    fun start() {
        inflate(res)
        showIcons()
        show()
    }

    private fun showIcons() {
        try {
            with(PopupMenu::class.java.getDeclaredField("mPopup")) {
                isAccessible = true
                get(this@CustomPopupMenu).apply {
                    javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(this, true)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}