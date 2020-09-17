import org.gradle.api.JavaVersion

object Config {
    const val application_id = "ru.storytellers"
    const val compile_sdk = 29
    const val min_sdk = 21
    const val target_sdk = 29
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}


object Modules {
    const val app = ":app"
    const val model = ":model"
    const val resources = ":resources"
}


object Versions {
    //Tools
    const val multidex = "1.0.3"

    //Cicerone
    const val cicerone="5.0.0"

    //Design
    const val appcompat = "1.1.0-rc01"
    const val material = "1.0.0"
    const val swiperefreshlayout = "1.0.0"
    const val constraintlayout = "1.1.3"

    //Kotlin
    const val core = "1.0.2"
    const val stdlib = "1.3.41"
    const val coroutinesCore = "1.2.1"
    const val coroutinesAndroid = "1.1.1"

    //Retrofit
    const val retrofit = "2.6.0"
    const val converterGson = "2.6.0"
    const val interceptor = "3.12.1"
    const val adapterCoroutines = "0.9.2"
    const val adapter_rxjava = "3.0.0"

    //Koin
    const val koinAndroid = "2.0.1"
    const val koinViewModel = "2.0.1"

    //Google Play
    const val googlePlayCore = "1.6.3"

    //Glide
    const val glide = "4.11.0"
    const val glideCompiler = "4.11.0"

    //Picasso
    const val picasso = "2.5.2"

    //Room
    const val roomKtx = "2.2.5"
    const val runtime = "2.2.5"
    const val roomCompiler = "2.2.5"

    //Timber
    const val timber = "4.7.1"

    //RxJava
    const val rxjava = "3.0.1"
    const val rxandroid = "3.0.0"

    //Test
    const val jUnit = "4.12"
    const val runner = "1.2.0"
    const val ext = "1.1.1"
    const val espressoCore = "3.2.0"
    const val coreTest="1.3.0"
    const val robolectric = "4.4"
    const val archCoreTest = "2.1.0"
    const val junitKtx = "1.1.2"
    const val koinTest = "2.0.1"
    const val mockk = "1.10.0"

    //Fragment
    const val lifecycle = "2.2.0"
    const val fragment = "1.2.5"

    //GifImageView
    const val gifImageView = "1.2.19"

    //Amplitude
    const val amplitudeVer = "2.23.2"
}

object Tools {
    const val multidex = "com.android.support:multidex:${Versions.multidex}"
}

object Android_x{
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
}

object Cicerone{
    const val cicerone="ru.terrakok.cicerone:cicerone:${Versions.cicerone}"

}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
}

object GooglePlay {
    const val googlePlayCore = "com.google.android.play:core:${Versions.googlePlayCore}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Timber{
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object RxJava{
    const val rxjava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
    const val rxandroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxandroid}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
    const val adapter_rxjava = "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.adapter_rxjava}"

}

object Koin {
    const val koin_android = "org.koin:koin-android:${Versions.koinAndroid}"
    const val koin_view_model = "org.koin:koin-android-viewmodel:${Versions.koinViewModel}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glideCompiler}"
}


object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"

    const val ext = "androidx.test.ext:junit:${Versions.ext}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val coreTest = "androidx.test:core-ktx:${Versions.coreTest}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    const val junitKtx = "androidx.test.ext:junit-ktx:${Versions.junitKtx}"
    const val koinTest = "org.koin:koin-test:${Versions.koinTest}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
}

object GifImageView {
    const val gifImageView = "pl.droidsonroids.gif:android-gif-drawable:${Versions.gifImageView}"
}

object Amplitude {
    const val amplitude = "com.amplitude:android-sdk:${Versions.amplitudeVer}"
}
