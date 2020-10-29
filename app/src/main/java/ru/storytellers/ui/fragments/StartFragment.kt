package ru.storytellers.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.StepActivity
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.AlertDialogFragment
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.StartViewModel

const val DIALOG_TAG_EXIT = "start-fragment-exit-46bf-ab6"
const val DIALOG_TAG_POLICY = "start-fragment-policy-46bf-ab6"

class StartFragment : BaseFragment<DataModel>() {
    override val layoutRes = R.layout.fragment_start
    override val model: StartViewModel by inject()
    private var sharedPreferences: SharedPreferences? = null


    companion object {
        fun newInstance() = StartFragment()
    }

    override fun init() {
        checkAgreement()
        rules_button.setOnClickListener { navigateToRulesGame() }
        about_button.setOnClickListener { navigateToAboutScreen() }
        new_tale_button.setOnClickListener { navigateToLevelScreen() }
        library_button.setOnClickListener { navigateToLibraryScreen() }
        rate_button.setOnClickListener { navigateToGooglePlay() }
        how_play_button.setOnClickListener {
            val intent = Intent(context, StepActivity::class.java)
            startActivity(intent)
        }

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
        model.getAllStory()
    }

    override fun onResume() {
        super.onResume()
        iniViewModel()
    }

    override fun iniViewModel() {
        model.subscribeOnSuccess().observe(viewLifecycleOwner, Observer {
            it.data?.let { listStoryLocal ->
                model.onStartScreenNumberOfTaleStat(listStoryLocal.count())
                if (listStoryLocal.isNotEmpty()) {
                    library_button.visibility = View.VISIBLE
                }
            }
        })

        model.subscribeOnError().observe(viewLifecycleOwner, Observer {
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