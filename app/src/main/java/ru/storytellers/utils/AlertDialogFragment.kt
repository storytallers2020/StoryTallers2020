package ru.storytellers.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentActivity
import ru.storytellers.R
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.fragments.LibraryFragment

class AlertDialogFragment(
    private val libraryFragment: LibraryFragment,
    private val story: Story
) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = libraryFragment.activity as FragmentActivity
        return getAlertDialog(context)
    }

    companion object {
        fun newInstance(libraryFragment: LibraryFragment, story: Story) =
            AlertDialogFragment(libraryFragment, story)
    }

    private fun getAlertDialog(context: Context) =
        context?.let { AlertDialog.Builder(it) }
            .setTitle(R.string.dialog_title)
            .setCancelable(true)
            .setNegativeButton(R.string.negative_answer) { dialog, _ ->
                dialog.dismiss()
            }

            .setPositiveButton(R.string.positive_answer) { dialog, _ ->
                libraryFragment.model.removeStory(story)
                dialog.dismiss()
            }
            .setPositiveButtonIcon(context.resources.getDrawable(R.drawable.ic_edit_bin))
            .create()

}