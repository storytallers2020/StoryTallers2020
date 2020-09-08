package ru.storytellers.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.storytellers.R
import ru.storytellers.ui.fragments.LibraryBookFragment
import ru.storytellers.ui.fragments.LibraryFragment
import ru.storytellers.ui.fragments.TeamCharacterFragment

class AlertDialogFragment(private val fragment: Fragment, private val title: Int) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = fragment.activity as FragmentActivity
        return getAlertDialog(context, title)
    }

    companion object {
        fun newInstance(fragment: Fragment, title: Int = R.string.dialog_title) =
            AlertDialogFragment(fragment, title)
    }

    private fun getAlertDialog(context: Context, header: Int) =
        AlertDialog.Builder(context)
            .setTitle(header)
            .setCancelable(true)
            .setNegativeButton(R.string.negative_answer) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.positive_answer) { _, _ ->
                when (fragment) {
                    is LibraryFragment -> {
                        fragment.setStateRemoveStoryFlag()
                    }
                    is LibraryBookFragment -> {
                        fragment.setStateRemoveStoryFlag()
                    }
                    is TeamCharacterFragment -> {
                        fragment.removeCharacter()
                    }
                }
            }.create()

}