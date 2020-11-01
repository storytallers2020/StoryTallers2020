package ru.storytellers.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_about_app.*
import org.koin.android.ext.android.inject
import ru.storytellers.BuildConfig.VERSION_CODE
import ru.storytellers.BuildConfig.VERSION_NAME
import ru.storytellers.R
import ru.storytellers.navigation.Screens
import ru.storytellers.ui.BackButtonListener
import ru.storytellers.utils.shareText
import ru.terrakok.cicerone.Router

class AboutAppFragment : Fragment(), BackButtonListener {
    private val router: Router by inject()

    companion object {
        fun newInstance() = AboutAppFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        back_btn.setOnClickListener { backClicked() }
        setVersionText()
        rate_button.setOnClickListener { navigateToGooglePlay() }
        privacy_policy_btn.setOnClickListener { navigateToWeb(getString(R.string.privacy_policy_link)) }
        terms_of_use_btn.setOnClickListener { navigateToWeb(getString(R.string.terms_of_use_link)) }
        developers_btn.setOnClickListener { navigateToDevelopersScreen() }

        web_page_btn.setOnClickListener { navigateToWeb(getString(R.string.web_page_link)) }
        instagram_btn.setOnClickListener { navigateToWeb(getString(R.string.instagram_link)) }
        vkontakte_btn.setOnClickListener { navigateToWeb(getString(R.string.vkontakte_link)) }
        odnoklassniki_btn.setOnClickListener { navigateToWeb(getString(R.string.odnoklassniki_link)) }
        facebook_btn.setOnClickListener { navigateToWeb(getString(R.string.facebook_link)) }

        contact_us_btn.setOnClickListener { sendEmail() }
        share_btn.setOnClickListener { shareApp() }
    }

    private fun setVersionText() {
        version.text = getString(R.string.version_template, VERSION_CODE, VERSION_NAME)
    }

    private fun navigateToGooglePlay() {
        val packageName = context?.packageName
        try {
            startActivity(
                withFlags(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.uri_to_market_google_play, packageName))
                    )
                )
            )
        } catch (e: ActivityNotFoundException) {
            navigateToWeb(getString(R.string.uri_to_http_google_play, packageName))
        }
    }

    private fun navigateToWeb(link: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }

    private fun navigateToDevelopersScreen() {
        router.navigateTo(Screens.AboutDevelopersScreen())
    }

    private fun sendEmail() {
        Uri.parse(getString(R.string.mailto))
            .buildUpon()
            .appendQueryParameter(getString(R.string.to), getString(R.string.email_address))
            .appendQueryParameter(getString(R.string.subject), getString(R.string.email_subject))
            .build().apply {
                startActivity(
                    Intent.createChooser(
                        withFlags(Intent(Intent.ACTION_SENDTO, this)),
                        getString(R.string.email_chooser_title)
                    )
                )
            }
    }

    private fun withFlags(intent: Intent) = intent.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )

    private fun shareApp() {
        shareText(
            this,
            getString(
                R.string.share_body,
                getString(R.string.uri_to_http_google_play, context?.packageName)
            )
        )
    }

    override fun backClicked(): Boolean {
        router.exit()
        return true
    }
}