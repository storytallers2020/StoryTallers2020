package ru.storytellers.di

import androidx.room.Room
import com.amplitude.api.Amplitude
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.storytellers.BuildConfig
import ru.storytellers.engine.Game
import ru.storytellers.engine.GameStorage
import ru.storytellers.engine.level.Level
import ru.storytellers.engine.level.Levels
import ru.storytellers.engine.rules.NoEmptySentenceRule
import ru.storytellers.engine.rules.Rules
import ru.storytellers.engine.showRules.IShowRule
import ru.storytellers.engine.showRules.ShowAllSentencesRule
import ru.storytellers.engine.showRules.ShowLastSentenceRule
import ru.storytellers.engine.wordRules.IWordRule
import ru.storytellers.engine.wordRules.RandomWordRule
import ru.storytellers.model.datasource.*
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.datasource.resourcestorage.CharacterResDataSource
import ru.storytellers.model.datasource.resourcestorage.CoverResDataSource
import ru.storytellers.model.datasource.resourcestorage.LocationResDataSource
import ru.storytellers.model.datasource.resourcestorage.storage.CharacterStorage
import ru.storytellers.model.datasource.resourcestorage.storage.CoverStorage
import ru.storytellers.model.datasource.resourcestorage.storage.LocationStorage
import ru.storytellers.model.datasource.resourcestorage.storage.WordStorage
import ru.storytellers.model.datasource.room.PlayerDataSource
import ru.storytellers.model.datasource.room.SentenceOfTaleDataSource
import ru.storytellers.model.datasource.room.StoryDataSource
import ru.storytellers.model.entity.room.db.AppDatabase
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.model.repository.*
import ru.storytellers.ui.assistant.TitleAndSaveModelAssistant
import ru.storytellers.ui.fragments.EditingFairyTaleFragment
import ru.storytellers.utils.AmplitudeWrapper
import ru.storytellers.utils.NetworkStatus
import ru.storytellers.utils.PlayerCreator
import ru.storytellers.viewmodels.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(
        listOf(
            ciceroneModule,
            databaseModule,
            startModule,
            levelModel,
            characterCreateModule,
            locationModule,
            gameEngine,
            gameModule,
            gameEndModule,
            selectCoverModule,
            titleAndSaveModule,
            libraryModule,
            libraryBookModule,
            editingFairyTaleModule,
            teamCharacterModule,
            gameStartModule,
            amplitudeModule,
            remoteModule
        )
    )
}

val libraryModule = module {
    viewModel { LibraryViewModel(get()) }
}
val libraryBookModule = module {
    viewModel { LibraryBookViewModel(get(),get()) }
}

val editingFairyTaleModule = module {
    viewModel { EditingFairyTaleViewModel(get(),get()) }
}

val ciceroneModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
}

val startModule = module {
    viewModel { StartViewModel(get()) }
}
val levelModel = module {
    viewModel { LevelViewModel() }
}
val gameStartModule = module {
    viewModel { GameStartViewModel(get()) }
}

val teamCharacterModule = module {
    viewModel { TeamCharacterViewModel() }
}

val characterCreateModule = module {
    single { PlayerCreator() }
    single<ICharacterDataSource> { CharacterResDataSource(get()) }
    single<ICharacterRepository> { CharacterRepository(get(), get(), get()) }
    viewModel { CharacterCreateViewModel(get(), get()) }
}

val locationModule = module {
    single { CharacterStorage(get()) }
    single { LocationStorage(get()) }
    single<ILocationDataSource> { LocationResDataSource(get()) }
    single<INetworkStatus> { NetworkStatus(get()) }
    single<ILocationRepository> { LocationRepository(get(), get(), get()) }
    viewModel { LocationViewModel(get()) }
}

val selectCoverModule = module {
    single { CoverStorage(get()) }
    single<ICoverDataSource> { CoverResDataSource(get()) }
    single<ICoverRepository> { CoverRepository(get(), get(), get()) }
    viewModel { SelectCoverViewModel(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "StoryTellers.db")
            .build()
    }
    single { get<AppDatabase>().characterDao }
    single { get<AppDatabase>().sentenceOfTaleDao }
    single { get<AppDatabase>().locationDao }
    single { get<AppDatabase>().storyDao }
    single { get<AppDatabase>().userDao }
}

val gameEndModule = module {
    viewModel { GameEndViewModel() }
}


val titleAndSaveModule = module {
    single<IStoryDataSource> { StoryDataSource(get(), get(), get()) }
    single<IStoryRepository> { StoryRepository(get()) }
    single { TitleAndSaveModelAssistant(get()) }
    viewModel { TitleAndSaveStoryViewModel(get()) }
}
val gameModule = module {
    viewModel { GameViewModel(get()) }
}

val gameEngine = module {
    single {
        val rule = Rules()
        rule.addRule(NoEmptySentenceRule())
//        rule.addRule(OneSentenceInTextRule())
        rule
    }

    single<IShowRule>(named("ShowAllSentencesRule")) { ShowAllSentencesRule() }
    single<IShowRule>(named("ShowLastSentenceRule")) { ShowLastSentenceRule() }

    single { WordStorage(get()) }
    single<IWordRule>(named("NoNeedWord")) { RandomWordRule(false, get()) }
    single<IWordRule>(named("NeedWord")) { RandomWordRule(true, get()) }

    single {
        Levels().apply {
            addLevel(
                Level(
                    0,
                    get(),
                    get(named("ShowAllSentencesRule")),
                    get(named("NoNeedWord"))
                )
            )
            addLevel(
                Level(
                    1,
                    get(),
                    get(named("ShowLastSentenceRule")),
                    get(named("NoNeedWord"))
                )
            )
            addLevel(
                Level(
                    2,
                    get(),
                    get(named("ShowLastSentenceRule")),
                    get(named("NeedWord"))
                )
            )
        }
    }

    single<IPlayerDataSource> { PlayerDataSource(get(), get()) }
    single<ISentenceOfTaleDataSource> { SentenceOfTaleDataSource(get(), get()) }
    single<ISentenceOfTaleRepository>{ SentenceOfTaleRepository(get()) }
    single { Game() }
    single { GameStorage() }

}

val amplitudeModule = module {
    single {
        Amplitude
            .getInstance()
            .initialize(get(), "ab4e83e9cbfca24360e8972402b8a412")
            .enableForegroundTracking(get())
    }
    single { AmplitudeWrapper(get()) }
}

val remoteModule = module {

    val BASE_URL = "http://storyheroes.online/api/"

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    single {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
            .create(IRemoteDataSource::class.java)
    }
}