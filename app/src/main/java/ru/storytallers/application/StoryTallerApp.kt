package ru.storytallers.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class StoryTallerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { androidContext(this@StoryTallerApp) }
        Timber.plant(Timber.DebugTree())
    }
}