package ru.storytellers.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.storytellers.ui.fragments.CreateCharacterFragment
import ru.storytellers.ui.fragments.LevelFragment
import ru.storytellers.ui.fragments.LocationFragment
import ru.storytellers.ui.fragments.StartFragment
import ru.storytellers.viewmodels.CreateCharacterViewModel
import ru.storytellers.viewmodels.LevelViewModel
import ru.storytellers.viewmodels.LocationViewModel
import ru.storytellers.viewmodels.StartViewModel
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(listOf(
        application,
        database,
        startmodel,
        levelmodel,
        charactermodel,
        locationmodel))
}

val application = module {
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
