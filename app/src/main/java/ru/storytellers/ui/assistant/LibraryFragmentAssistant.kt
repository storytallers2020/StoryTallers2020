package ru.storytellers.ui.assistant

import ru.storytellers.R
import ru.storytellers.ui.fragments.LibraryFragment
import ru.storytellers.utils.AlertDialogFragment
import ru.storytellers.utils.concatTitleAndTextStory
import ru.storytellers.utils.copyText
import ru.storytellers.utils.shareText

private const val FRAGMENT_DIALOG_TAG = "library-5d62-46bf-ab6"

class LibraryFragmentAssistant(
    private val fragment: LibraryFragment
) {
    fun shareText(textStory: String?, titleStory: String?) {
        titleStory?.let { title ->
            textStory?.let { text ->
                with(concatTitleAndTextStory(title, text, fragment.getString(R.string.msg_share))) {
                    if (this.isNotBlank()) shareText(
                        fragment,
                        this
                    )
                }
            }
        }
    }

    fun copyText(textStory: String?) {
        textStory?.let { text ->
            copyText(fragment.requireContext(), text)
        }
    }

    fun createAndShowAlertDialog() {
        fragment.activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(fragment, R.string.dialog_story)
                .show(fragMan, FRAGMENT_DIALOG_TAG)
        }
    }
}