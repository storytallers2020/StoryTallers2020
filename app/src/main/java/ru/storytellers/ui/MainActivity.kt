package ru.storytellers.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_choosing_title.*
import kotlinx.android.synthetic.main.fragment_game_end.*
import org.koin.android.ext.android.inject
import ru.storytellers.R
import ru.storytellers.di.injectDependencies
import ru.storytellers.navigation.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = SupportAppNavigator(this, R.id.container)
    private val router: Router by inject()
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mInterstitialAd2: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        injectDependencies()
        turnOffFullScreen()
        router.replaceScreen(Screens.StartScreen())

        MobileAds.initialize(this)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd2 = InterstitialAd(this)
        mInterstitialAd2.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd2.loadAd(AdRequest.Builder().build())
    }

    private fun turnOffFullScreen() {
        //fixes the bug with adjustResize not working with windowFullscreen
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    fun doStuff() {
        btn_next.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                router.navigateTo(Screens.GameScreen())
            }

        }
    }

    fun doStuff2() {
        btn_select_cover.setOnClickListener {
            if (mInterstitialAd2.isLoaded) {
                mInterstitialAd2.show()
            } else {
                router.navigateTo(Screens.SelectCoverScreen())
            }

        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backClicked()) {
                return
            }
        }
    }
}
