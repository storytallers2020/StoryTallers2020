package ru.storytellers.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import ru.storytellers.R

class CustomAgreementDialog(private val caller: DialogCaller) : CustomAlertDialog(caller) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = this.requireActivity()
        return getAlertDialog(context, R.string.agreement_dialog_title)
    }

    override fun getAlertDialog(context: Context, header: Int): AlertDialog {
        isCancelable = false
        val mView = layoutInflater.inflate(R.layout.layout_agreement, null)
        (mView.findViewById(R.id.layout_agreement_text) as TextView).movementMethod = LinkMovementMethod.getInstance()

        return AlertDialog.Builder(context)
                .setTitle(header)
                .setView(mView)
                .setNegativeButton(R.string.decline_answer) { _, _ -> caller.onDialogNegativeButton(tag) }
                .setPositiveButton(R.string.accept_answer) { dialog, _ ->
                    caller.onDialogPositiveButton(tag)
                    dialog.dismiss()
                }
                .create()
    }

}