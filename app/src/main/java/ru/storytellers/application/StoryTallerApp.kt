package ru.storytellers.application

import android.app.Application
import com.amplitude.api.Amplitude
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.storytellers.engine.Game
import ru.storytellers.engine.GameStorage
import ru.storytellers.engine.level.Levels
import ru.storytellers.utils.AmplitudeWrapper
import timber.log.Timber

class StoryTallerApp: Application() {


    companion object {
        lateinit var instance: StoryTallerApp
            private set
    }

    val levels: Levels by inject()
    val  gameStorage: GameStorage by inject()
    val stat: AmplitudeWrapper by inject()

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin { androidContext(this@StoryTallerApp) }
        Timber.plant(Timber.DebugTree())
    }
}