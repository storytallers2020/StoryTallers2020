package ru.storytellers.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import ru.storytellers.R
import timber.log.Timber

class AdMobFragment (private var context: Context){
    private lateinit var mInterstitialAd: InterstitialAd

    companion object {
        fun newInstance(context: Fragment): AdMobFragment {
            return context.requireActivity().baseContext.let { AdMobFragment(it) }
        }
    }

    fun buildAd() {
        MobileAds.initialize(context)
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = context.getString(R.string.ad_key_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    fun startAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Timber.e(context.getString(R.string.something_went_wrong))
        }
    }
}