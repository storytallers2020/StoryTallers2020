package ru.storytellers.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ru.storytellers.engine.GameStorage
import ru.storytellers.model.datasource.ICharacterDataSource
import ru.storytellers.model.datasource.resourcestorage.CharacterResDataSource
import ru.storytellers.engine.Game
import ru.storytellers.engine.level.Level
import ru.storytellers.engine.level.Levels
import ru.storytellers.engine.rules.NoEmptySentenceRule
import ru.storytellers.engine.rules.OneSentenceInTextRule
import ru.storytellers.engine.rules.Rules
import ru.storytellers.model.datasource.ILocationDataSource
import ru.storytellers.model.datasource.resourcestorage.LocationResDataSource
import ru.storytellers.viewmodels.CreateCharacterViewModel
import ru.storytellers.viewmodels.LevelViewModel
import ru.storytellers.viewmodels.LocationViewModel
import ru.storytellers.viewmodels.StartViewModel
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.model.repository.CharacterRepository
import ru.storytellers.model.repository.ICharacterRepository
import ru.storytellers.model.repository.ILocationRepository
import ru.storytellers.model.repository.LocationRepository
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.viewmodels.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(
        listOf(
            ciceroneModule,
            databaseModel,
            startModel,
            levelModel,
            characterModel,
            locationModel,
            gameModel
        )
    )
}

val ciceroneModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
}

val startModel =  module {
        viewModel { StartViewModel() }
}
val levelModel =  module {
        viewModel { LevelViewModel() }
}
val characterModel =  module {
    single { PlayerCreator() }
    single<ICharacterDataSource>{CharacterResDataSource(get()) }
    single<ICharacterRepository>{CharacterRepository(get()) }
    viewModel { CreateCharacterViewModel(get()) }
}

val locationModel =  module {
    single<ILocationDataSource>{ LocationResDataSource(get()) }
    single<ILocationRepository>{ LocationRepository(get()) }
    viewModel { LocationViewModel(get()) }
}

val databaseModel = module {
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

val gameModel = module {
    single {
        val rule = Rules()
        rule.addRule(NoEmptySentenceRule())
        rule.addRule(OneSentenceInTextRule())
        rule
    }
    single {
        val levels = Levels()
        levels.addLevel(Level(0, get()))
        levels.addLevel(Level(1, get()))
        levels.addLevel(Level(2, get()))
        levels
    }

    single { Game() }
    single { GameStorage() }
    viewModel { GameViewModel(get(),get()) }
}
