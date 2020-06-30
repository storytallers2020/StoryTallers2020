package ru.storytallers.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.storytallers.ui.fragments.CreateCharacterFragment
import ru.storytallers.ui.fragments.LevelFragment
import ru.storytallers.ui.fragments.LocationFragment
import ru.storytallers.ui.fragments.StartFragment
import ru.storytallers.viewmodels.CreateCharacterViewModel
import ru.storytallers.viewmodels.LevelViewModel
import ru.storytallers.viewmodels.LocationViewModel
import ru.storytallers.viewmodels.StartViewModel
import ru.storytallers.model.entity.room.db.AppDatabase
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(listOf(
        cicerone,
        database,
        startmodel,
        levelmodel,
        charactermodel,
        locationmodel))
}

val cicerone = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router}
    single { get<Cicerone<Router>>().navigatorHolder}
}

val startmodel =  module {
    scope(named<StartFragment>()) {
        viewModel { StartViewModel() }
    }
}
val levelmodel =  module {
    scope(named<LevelFragment>()) {
        viewModel { LevelViewModel() }
    }
}
val charactermodel =  module {
    scope(named<CreateCharacterFragment>()) {
        viewModel { CreateCharacterViewModel() }
    }
}

val locationmodel =  module {
    scope(named<LocationFragment>()) {
        viewModel { LocationViewModel() }
    }
}



val database = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "StoryTallersDB")
            .build()
    }
    single { get<AppDatabase>().characterDao }
    single { get<AppDatabase>().sentenceOfTaleDao }
    single { get<AppDatabase>().locationDao }
    single { get<AppDatabase>().storyDao }
    single { get<AppDatabase>().userDao }
}
