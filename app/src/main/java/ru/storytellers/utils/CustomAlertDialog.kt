package ru.storytellers.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import ru.storytellers.R


open class CustomAlertDialog(private val caller: DialogCaller, private val title: Int = R.string.dialog_title) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = this.requireActivity()
        return getAlertDialog(context, title)
    }

    open fun getAlertDialog(context: Context, header: Int): AlertDialog =
            AlertDialog.Builder(context)
                    .setTitle(header)
                    .setCancelable(true)
                    .setNegativeButton(R.string.negative_answer) { dialog, _ -> dialog.cancel() }
                    .setPositiveButton(R.string.positive_answer) { _, _ -> caller.onDialogPositiveButton(tag) }
                    .create()

    override fun onCancel(dialog: DialogInterface) {
        caller.onDialogNegativeButton(tag)
    }

}