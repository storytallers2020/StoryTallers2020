package ru.storytellers.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.splash_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.droidsonroids.gif.GifDrawable
import ru.storytellers.BuildConfig
import ru.storytellers.R
import ru.storytellers.di.injectDependencies
import ru.storytellers.utils.CustomAlertDialog
import ru.storytellers.utils.DialogCaller
import ru.storytellers.viewmodels.SplashViewModel
import kotlin.system.exitProcess

class SplashActivity : AppCompatActivity(), DialogCaller{

    val model: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        setVersionText()

        injectDependencies()

        setEvents()
        model.loadResource()
    }

    private fun setVersionText() {
        version_text.text = getString(R.string.version, "(${BuildConfig.VERSION_CODE}) ${BuildConfig.VERSION_NAME}")
    }


    private fun setEvents() {
        model.subscribeOnSuccess().observe(this, {
            startActivityWithDelay(getDelay())
        })

        model.subscribeOnError().observe(this, {
            loading_text.text = it.message
            supportFragmentManager.let { fragMan ->
                CustomAlertDialog(this, R.string.update_resource_error)
                    .show(fragMan, "TAG")
            }
        })

        model.subscribeOnLoading().observe(this, {
            val percent = "$it%"
            loading_text.text = percent
        })

        model.subscribeOnCloseApp().observe(this, { close ->
            if (close) {
                moveTaskToBack(true)
                exitProcess(-1)
            }
        })
    }

    private fun startActivityWithDelay(delay: Long) {
        Handler().postDelayed({
            finish()
        }, delay)
    }

    private fun getDelay(): Long {
        val splashGifDuration = GifDrawable(resources, R.drawable.splash_logo).duration
        val durationFactor = getString(R.string.factor).toDouble()
        return (splashGifDuration * durationFactor).toLong()
    }

    override fun onDialogPositiveButton(tag: String?) {
        model.onUserSelectContinue()
    }

    override fun onDialogNegativeButton(tag: String?) {
        model.onUserSelectClose()
    }
}