package ru.storytellers.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import pl.droidsonroids.gif.GifDrawable
import ru.storytellers.R
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    val factor = 1.5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, getDelay())
        Timber.e("${getDelay()}")
    }

    private fun getDelay() =
        (GifDrawable(resources, R.drawable.splash_logo).duration * factor).toLong()
}