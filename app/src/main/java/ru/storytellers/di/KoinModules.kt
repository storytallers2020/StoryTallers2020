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
import ru.storytellers.application.StoryHeroesApp
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
import ru.storytellers.model.cache.ICashImageDataSource
import ru.storytellers.model.cache.IImageCache
import ru.storytellers.model.datasource.*
import ru.storytellers.model.datasource.remote.IRemoteDataSource
import ru.storytellers.model.datasource.room.*
import ru.storytellers.model.datasource.storage.WordStorage
import ru.storytellers.model.image.IImageLoader
import ru.storytellers.model.network.INetworkStatus
import ru.storytellers.model.repository.*
import ru.storytellers.ui.assistant.TitleAndSaveModelAssistant
import ru.storytellers.ui.image.ImageLoader
import ru.storytellers.utils.AmplitudeWrapper
import ru.storytellers.model.cache.ImageCache
import ru.storytellers.model.entity.room.db.*
import ru.storytellers.model.repository.remote.RemoteRepository
import ru.storytellers.model.repository.remote.IRemoteRepository
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
            networkStatusModule,
            cacheModule,
            dataSourceModule,
            repositoryModule,
            splashModule,
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
            libraryBookShowModule,
            libraryBookEditModule,
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

val libraryBookShowModule = module {
    viewModel { LibraryBookShowViewModel(get()) }
}

val libraryBookEditModule = module {
    viewModel { LibraryBookEditViewModel(get(), get()) }
}

val ciceroneModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
}

val splashModule = module {
    viewModel { SplashViewModel(get(), get()) }
}

val startModule = module {
    viewModel { StartViewModel(get()) }
}

val levelModel = module {
    viewModel { LevelViewModel(get(), get()) }
}

val gameStartModule = module {
    viewModel { GameStartViewModel(get()) }
}

val teamCharacterModule = module {
    viewModel { TeamCharacterViewModel() }
}

val characterCreateModule = module {
    single { PlayerCreator() }
    viewModel { CharacterCreateViewModel(get(), get()) }
}

val locationModule = module {
    viewModel { LocationViewModel(get()) }
}

val selectCoverModule = module {
    viewModel { SelectCoverViewModel(get()) }
}
val networkStatusModule = module {
    single<INetworkStatus> { NetworkStatus(get()) }
}

val cacheModule = module {
    val file = StoryHeroesApp.instance.imageCashDir

    single<IImageCache> { ImageCache(get(), file) }
    single<IImageLoader> { ImageLoader(get()) }
}

val dataSourceModule = module {
    single<ICharacterDataSource> { CharacterDataSource(get()) }
    single<ILocationDataSource> { LocationDataSource(get()) }
    single<IStoryDataSource> { StoryDataSource(get()) }
    single<ICoverDataSource> { CoverDataSource(get()) }
    single<IVersionDataSource> { VersionDataSource(get()) }
    single<IWordDataSource> { WordDataSource(get()) }

    single<ICashImageDataSource> { CashImageDataSource(get(), get()) }
}

val repositoryModule = module {
    single<IRemoteRepository> { RemoteRepository(get(), get(), get(), get(), get(), get(), get()) }
    single<ICharacterRepository> { CharacterRepository(get()) }
    single<ILocationRepository> { LocationRepository(get() ) }
    single<IStoryRepository> { StoryRepository(get()) }
    single<ICoverRepository> { CoverRepository(get()) }
    single<IWordRepository> { WordRepository(get())}

    single<IVersionRepository> { VersionRepository(get(), get(), get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "StoryTellers.db")
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
            .build()
    }
    single { get<AppDatabase>().characterDao }
    single { get<AppDatabase>().sentenceOfTaleDao }
    single { get<AppDatabase>().locationDao }
    single { get<AppDatabase>().storyDao }
    single { get<AppDatabase>().userDao }
    single { get<AppDatabase>().versionsDao }
    single { get<AppDatabase>().wordDao }
}

val gameEndModule = module {
    viewModel { GameEndViewModel() }
}

val titleAndSaveModule = module {
    single { TitleAndSaveModelAssistant(get()) }
    viewModel { TitleAndSaveStoryViewModel(get()) }
}

val gameModule = module {
    viewModel { GameViewModel(get(), get()) }
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

    single { WordStorage() }
    single<IWordRule>(named("NoNeedWord")) { RandomWordRule(false) }
    single<IWordRule>(named("NeedWord")) { RandomWordRule(true) }

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
    single<ISentenceOfTaleRepository> { SentenceOfTaleRepository(get()) }
    single { Game(get()) }
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
