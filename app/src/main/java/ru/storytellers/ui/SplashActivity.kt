package ru.storytellers.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.splash_activity.*
import pl.droidsonroids.gif.GifDrawable
import ru.storytellers.BuildConfig
import ru.storytellers.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        setVersionText()
        startActivityWithDelay(getDelay())
    }

    private fun setVersionText() {
        version_text.text =
            getString(R.string.version, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME)
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