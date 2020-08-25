package ru.storytellers.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentActivity
import ru.storytellers.R
import ru.storytellers.ui.fragments.LibraryFragment

class AlertDialogFragment(
    private val libraryFragment: LibraryFragment,
    private val storyId:Long
): AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = libraryFragment.activity as FragmentActivity
        return  getAlertDialog(context)
    }

    companion object {
        fun newInstance(libraryFragment: LibraryFragment,storyId:Long) = AlertDialogFragment(libraryFragment,storyId)
    }
    private fun getAlertDialog(context: Context): AlertDialog {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.apply {
            setTitle(R.string.dialog_title)
            setNegativeButton(R.string.negative_answer){dialog, _ -> dialog.dismiss()}
            setCancelable(false)
            setPositiveButton(R.string.positive_answer) { dialog, _ ->
                libraryFragment.model.removeStory(storyId)
                dialog.dismiss()
            }
        }
        return builder?.create()
    }
}