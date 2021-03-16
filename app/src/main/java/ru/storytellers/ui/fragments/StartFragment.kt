package ru.storytellers.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.model.entity.UserAccountData
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.AlertDialogFragment
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.StartViewModel

const val DIALOG_TAG_EXIT = "start-fragment-exit-46bf-ab6"
const val DIALOG_TAG_POLICY = "start-fragment-policy-46bf-ab6"
const val RC_SIGN_IN = 4242

class StartFragment : BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override val model: StartViewModel by inject()
    private var sharedPreferences: SharedPreferences? = null
    private val googleSignInClient: GoogleSignInClient by inject()


    companion object {
        fun newInstance() = StartFragment()
    }

    override fun init() {
        checkAgreement()
        sign_in_button.setOnClickListener { onSignInClick() }
        rules_button.setOnClickListener { navigateToRulesGame() }
        about_button.setOnClickListener { navigateToAboutScreen() }
        new_tale_button.setOnClickListener { navigateToLevelScreen() }
        library_button.setOnClickListener { navigateToLibraryScreen() }
        rate_button.setOnClickListener { navigateToGooglePlay() }
    }


    private fun navigateToLevelScreen() {
        model.createTaleStatistics()
        model.timeCreateStory()
        router.navigateTo(Screens.LevelScreen())
    }

    private fun navigateToGooglePlay() {
        val packageName = context?.packageName
        try {
            startActivity(
                    Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.uri_to_market_google_play, packageName))
                    ).apply {
                        addFlags(
                                Intent.FLAG_ACTIVITY_NO_HISTORY or
                                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                        )
                    }
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                    Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.uri_to_http_google_play, packageName))
                    )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        val account = activity?.let { GoogleSignIn.getLastSignedInAccount(it) }
        account?.apply {
            sign_in_button.visibility = View.GONE
        }
        model.getAllStory()
    }

    override fun onResume() {
        super.onResume()
        initViewModel()
    }

    private fun onSignInClick() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            account?.let { getUserDataFromAccount(it) }
        } catch (er: ApiException) {
            Toast.makeText(activity, er.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun getUserDataFromAccount(account: GoogleSignInAccount) {
        val userAccountData = account?.run {
            UserAccountData(
                    displayName ?: "No name",
                    email ?: "No email",
                    id ?: "No id",
                    idToken ?: "No idToken",
                    photoUrl
            )
         }
    }

    override fun initViewModel() {
        model.subscribeOnSuccess().observe(viewLifecycleOwner, {
            it.data?.let { listStoryLocal ->
                model.onStartScreenNumberOfTaleStat(listStoryLocal.count())
                if (listStoryLocal.isNotEmpty()) {
                    library_button.visibility = View.VISIBLE
                }
            }
        })

        model.subscribeOnError().observe(viewLifecycleOwner, {
            activity?.let { context ->
                toastShowLong(
                        context,
                        context.getString(R.string.something_went_wrong)
                )
            }
        })
    }

    private fun checkAgreement() {
        sharedPreferences = activity?.getSharedPreferences(DIALOG_TAG_POLICY, 0)
        sharedPreferences?.getBoolean(DIALOG_TAG_POLICY, false)?.let {
            if (!it) {
                showAlertDialog(DIALOG_TAG_POLICY, 0)
            }
        }
    }

    private fun showAlertDialog(tag: String, title: Int) {
        activity?.supportFragmentManager?.let { manager ->
            AlertDialogFragment.newInstance(this, title).show(manager, tag)
        }
    }

    fun acceptAgreement() {
        sharedPreferences?.edit()?.putBoolean(DIALOG_TAG_POLICY, true)?.apply()
    }

    private fun navigateToRulesGame() {
        model.toRulesScreenStatistics()
        router.navigateTo(Screens.RulesGameScreen())
    }

    private fun navigateToAboutScreen() {
        model.onAboutScreenStatistics()
        router.navigateTo(Screens.AboutAppScreen())
    }

    private fun navigateToLibraryScreen() {
        model.onLibraryScreenStatistics()
        router.navigateTo(Screens.LibraryScreen())
    }

    override fun backClicked(): Boolean {
        showAlertDialog(DIALOG_TAG_EXIT, R.string.dialog_exit)
        return false
    }

    fun closeApplication() {
        router.exit()
    }
}