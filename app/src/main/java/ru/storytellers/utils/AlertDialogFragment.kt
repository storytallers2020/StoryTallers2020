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
import ru.storytellers.ui.fragments.LibraryBookFragment
import ru.storytellers.ui.fragments.LibraryFragment
import ru.storytellers.ui.fragments.StartFragment
import ru.storytellers.ui.fragments.TeamCharacterFragment

class AlertDialogFragment(private val fragment: Fragment, private val title: Int) : AppCompatDialogFragment() {

    companion object {
        fun newInstance(fragment: Fragment, title: Int = R.string.dialog_title) =
            AlertDialogFragment(fragment, title)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = fragment.activity as FragmentActivity
        return if (fragment is StartFragment) {
            getAgreementDialog(context)
        } else {
            getAlertDialog(context, title)
        }
    }

    private fun getAlertDialog(context: Context, header: Int) =
        AlertDialog.Builder(context)
            .setTitle(header)
            .setCancelable(true)
            .setNegativeButton(R.string.negative_answer) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(R.string.positive_answer) { _, _ ->
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
                }
            }.create()

    private fun getAgreementDialog(context: Context): Dialog {
        isCancelable = false
        val mView = layoutInflater.inflate(R.layout.layout_agreement, null)
        (mView.findViewById(R.id.layout_agreement_text) as TextView).movementMethod =
            LinkMovementMethod.getInstance()
        return AlertDialog.Builder(context)
            .setTitle(R.string.agreement_dialog_title)
            .setView(mView)
            .setNegativeButton(R.string.decline_answer) { dialog, _ ->
                toastShowLong(context, getString(R.string.msg_agreement_declined))
                if (fragment is StartFragment) fragment.backClicked()
                dialog.cancel()

            }
            .setPositiveButton(R.string.accept_answer) { dialog, _ ->
                if (fragment is StartFragment) fragment.acceptAgreement()
                dialog.dismiss()
            }
            .create()
    }

}