package ru.storytellers.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class StoryTallerApp: Application() {

    companion object {
        private lateinit var application: StoryTallerApp
        fun getInstanceApp() = application
    }

    override fun onCreate() {
        super.onCreate()
        application=this
        startKoin { androidContext(this@StoryTallerApp) }
        Timber.plant(Timber.DebugTree())
    }
}