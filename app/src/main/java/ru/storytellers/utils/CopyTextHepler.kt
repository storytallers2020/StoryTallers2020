package ru.storytellers.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import ru.storytellers.R
import timber.log.Timber

fun String.setTextToClipboard(context: Context): Boolean {
    try {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", this)
        clipboard.setPrimaryClip(clip)
    } catch (e: Exception) {
        Timber.e(e)
        return false
    }

    return true
}

fun shareText(fragment: Fragment, resultText: String) {
    with(Intent()) {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, resultText)
        type = fragment.getString(R.string.intent_text_plain)
        fragment.startActivity(Intent.createChooser(this, fragment.getString(R.string.share_bottom_sheet_title)))
    }
}

fun copyText(context: Context, textStory: String) = textStory.setTextToClipboard(context)

fun concatTitleAndTextStory(title: String, text: String, msgShare: String): String {
    val concatText = StringBuilder()
    return concatText.append(title)
        .append("\n")
        .append(text)
        .append("\n")
        .append(msgShare)
        .toString()

}

