package ru.storytellers.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.storytellers.R
import ru.storytellers.ui.fragments.*

class AlertDialogFragment(private val fragment: Fragment, private val title: Int) : AppCompatDialogFragment() {

    companion object {
        fun newInstance(fragment: Fragment, title: Int = R.string.dialog_title) =
            AlertDialogFragment(fragment, title)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = fragment.activity as FragmentActivity
        return when (tag) {
            DIALOG_TAG_POLICY -> getAgreementDialog(context)
            else -> getAlertDialog(context, title)
        }
    }

    private fun getAlertDialog(context: Context, header: Int) =
        AlertDialog.Builder(context)
            .setTitle(header)
            .setCancelable(true)
            .setNegativeButton(R.string.negative_answer) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(R.string.positive_answer) { dialog, _ ->
                when (fragment) {
                    is LibraryFragment -> {
                        fragment.deleteStory()
                    }
                    is LibraryBookFragment -> {
                        fragment.setStateRemoveStoryFlag()
                    }
                    is TeamCharacterFragment -> {
                        fragment.removeCharacter()
                    }
                    is StartFragment -> {
                        toastShowLong(context, getString(R.string.msg_on_exit))
                        fragment.closeApplication()
                    }
                    else -> {
                        toastShowLong(context, getString(R.string.something_went_wrong))
                        dialog.dismiss()
                    }
                }
            }.create()

    private fun getAgreementDialog(context: Context): Dialog {
        isCancelable = false
        if (fragment is StartFragment) {
            val mView = layoutInflater.inflate(R.layout.layout_agreement, null)
            (mView.findViewById(R.id.layout_agreement_text) as TextView).movementMethod =
                LinkMovementMethod.getInstance()
            return AlertDialog.Builder(context)
                .setTitle(R.string.agreement_dialog_title)
                .setView(mView)
                .setNegativeButton(R.string.decline_answer) { dialog, _ ->
                    toastShowLong(context, getString(R.string.msg_agreement_declined))
                    fragment.closeApplication()
                    dialog.cancel()

                }
                .setPositiveButton(R.string.accept_answer) { dialog, _ ->
                    toastShowShort(context, getString(R.string.msg_agreement_accepted))
                    fragment.acceptAgreement()
                    dialog.dismiss()
                }
                .create()
        } else {
            return getAlertDialog(context, R.string.dialog_title)
        }

    }

}