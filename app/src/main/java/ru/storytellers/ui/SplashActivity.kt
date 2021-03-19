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
import ru.storytellers.viewmodels.SplashViewModel

class SplashActivity : AppCompatActivity() {

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
            //TODO: Show Error Message
            startActivityWithDelay(getDelay())
        })

        model.subscribeOnLoading().observe(this, {
            //TODO: Add loading progress to xml
            val percent = "$it%"
            version_text.text = percent
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
}