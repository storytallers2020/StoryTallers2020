package ru.storytellers.ui.fragments

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.model.DataModel
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.fragments.basefragment.BaseFragment
import ru.storytellers.utils.AlertDialogFragment
import ru.storytellers.utils.toastShowLong
import ru.storytellers.viewmodels.StartViewModel
import timber.log.Timber

private const val FRAGMENT_DIALOG_TAG = "start-5d62-46bf-ab6"

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
        new_tale_button.setOnClickListener{ navigateToLevelScreen() }
        library_button.setOnClickListener { navigateToLibraryScreen() }
    }

    private fun navigateToLevelScreen() {
        model.createTaleStatistics()
        router.navigateTo(Screens.LevelScreen())
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
        sharedPreferences = activity?.getSharedPreferences(FRAGMENT_DIALOG_TAG, 0)
        sharedPreferences?.getBoolean(FRAGMENT_DIALOG_TAG, false)?.let {
            if (!it) {
                showAlertDialog()
            }
        }
    }

    private fun showAlertDialog() {
        activity?.supportFragmentManager?.let { fragMan ->
            AlertDialogFragment.newInstance(this).show(fragMan, FRAGMENT_DIALOG_TAG)
        }
    }

    fun acceptAgreement() {
        sharedPreferences?.edit()?.putBoolean(FRAGMENT_DIALOG_TAG, true)?.apply()
        Timber.e(
            "Alert: AgreementAccepted: %s",
            sharedPreferences?.getBoolean(FRAGMENT_DIALOG_TAG, false)
        )
    }

    private fun navigateToRulesGame() {
        model.toRulesScreenStatistics()
        router.navigateTo(Screens.RulesGameScreen())
    }

    private fun navigateToAboutScreen() {
        model.onAboutScreenStatistics()
        router.navigateTo(Screens.AppInfoScreen())
    }

    private fun navigateToLibraryScreen(){
        model.onLibraryScreenStatistics()
        router.navigateTo(Screens.LibraryScreen())
    }

    override fun backClicked(): Boolean {
        router.exit()
        return false
    }
}