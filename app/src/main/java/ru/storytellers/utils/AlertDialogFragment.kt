package ru.storytellers.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.storytellers.R
import ru.storytellers.model.entity.Story
import ru.storytellers.ui.fragments.LibraryBookFragment
import ru.storytellers.ui.fragments.LibraryFragment

class AlertDialogFragment(
    private val fragment: Fragment,
    private val story: Story
) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = fragment.activity as FragmentActivity
        return getAlertDialog(context)
    }

    companion object {
        fun newInstance(fragment: Fragment, story: Story) =
            AlertDialogFragment(fragment, story)
    }

    private fun getAlertDialog(context: Context) =
        AlertDialog.Builder(context)
            .setTitle(R.string.btn_delete)
            .setMessage(R.string.dialog_title)
            .setCancelable(true)
            .setNegativeButton(R.string.negative_answer) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.positive_answer) { _, _ ->
                when (fragment) {
                    is LibraryFragment -> {
                        fragment.model.removeStory(story)
                    }
                    is LibraryBookFragment -> {
                        fragment.model.removeStory(story)
                    }
                }
            }.create()

}