package ru.storytellers.application

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.storytellers.engine.GameStorage
import ru.storytellers.engine.level.Levels
import ru.storytellers.utils.AmplitudeWrapper
import timber.log.Timber
import java.io.File

class StoryHeroesApp : Application() {


    companion object {
        lateinit var instance: StoryHeroesApp
            private set
    }

    val levels: Levels by inject()
    val gameStorage: GameStorage by inject()
    val stat: AmplitudeWrapper by inject()

    lateinit var imageCashDir: File

    override fun onCreate() {
        super.onCreate()

        instance = this

        // mnt\sdcard\Android\data\ru.storytellers\cache\image_cache
        val path = (externalCacheDir?.path
            ?: getExternalFilesDir(null)?.path
            ?: filesDir.path) + File.separator + "image_cache"
        imageCashDir = File(path)

        startKoin { androidContext(this@StoryHeroesApp) }
        Timber.plant(Timber.DebugTree())
    }
}