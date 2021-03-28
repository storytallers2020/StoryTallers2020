package ru.storytellers.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.splash_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.droidsonroids.gif.GifDrawable
import ru.storytellers.BuildConfig
import ru.storytellers.R
import ru.storytellers.di.injectDependencies
import ru.storytellers.ui.fragments.StartFragment
import ru.storytellers.utils.CustomAlertDialog
import ru.storytellers.utils.DialogCaller
import ru.storytellers.viewmodels.SplashViewModel

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
        version_text.text =
            getString(R.string.version, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME)
    }


    private fun setEvents() {
        model.subscribeOnSuccess().observe(this, {
            startActivityWithDelay(getDelay())
        })

        model.subscribeOnError().observe(this, {
            //TODO: Show Error Message and exit
            supportFragmentManager.let { fragMan ->
                CustomAlertDialog(this, R.string.game_loading_msg).show(fragMan, "TAG")
            }
            startActivityWithDelay(getDelay())
        })

        model.subscribeOnLoading().observe(this, {
            val percent = "$it%"
            loading_text.text = percent
        })
    }

    private fun startActivityWithDelay(delay: Long) {
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, delay)
    }

    private fun getDelay(): Long {
        val splashGifDuration = GifDrawable(resources, R.drawable.splash_logo).duration
        val durationFactor = getString(R.string.factor).toDouble()
        return (splashGifDuration * durationFactor).toLong()
    }

    override fun onDialogPositiveButton(tag: String?) {
        TODO("Not yet implemented")
    }

    override fun onDialogNegativeButton(tag: String?) {
        TODO("Not yet implemented")
    }
}