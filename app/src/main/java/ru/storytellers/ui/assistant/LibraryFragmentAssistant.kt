package ru.storytellers.ui.assistant

import ru.storytellers.R
import ru.storytellers.ui.fragments.LibraryFragment
import ru.storytellers.utils.AlertDialogFragment
import ru.storytellers.utils.concatTitleAndTextStory
import ru.storytellers.utils.copyText
import ru.storytellers.utils.shareText

private const val FRAGMENT_DIALOG_TAG = "library-5d62-46bf-ab6"

class LibraryFragmentAssistant(private val fragment: LibraryFragment) {
    private var storyTitle: String = ""
    private var storyText: String = ""

    fun setStoryTitle(title: String) {
        storyTitle = title
    }

    fun setStoryText(text: String) {
        storyText = text
    }

    fun shareText() {
        with(
            concatTitleAndTextStory(
                storyTitle,
                storyText,
                fragment.getString(R.string.msg_share)
            )
        ) {
            if (this.isNotBlank())
                shareText(fragment, this)
        }
    }

    fun copyText() {
        with(
            concatTitleAndTextStory(
                storyTitle,
                storyText,
                fragment.getString(R.string.msg_share)
            )
        ) {
            if (this.isNotBlank())
                copyText(fragment.requireContext(), this)
        }
    }

    fun showAlertDialog() {
        fragment.activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(fragment, R.string.dialog_story)
                .show(fragMan, FRAGMENT_DIALOG_TAG)
        }
    }
}