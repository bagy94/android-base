object Android {
    object V {
        const val buildTools = "3.4.1"
        const val kotlin = "1.3.40"
    }

    //Build gradle classpath
    const val buildTools = "com.android.tools.build:gradle:${V.buildTools}"
    //Kotlin classpath
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${V.kotlin}"
    //Default config
    const val compileSDK = 28
    const val minSDK = 21
    //MAven
    const val maven = "com.github.dcendents:android-maven-gradle-plugin:2.1"
}

object Plugins {
    //Main app
    const val application = "com.android.application"
    //Libs
    const val library = "com.android.library"
    //Kotlin android
    const val kotlin = "kotlin-android"
    //Kotlin extensions
    const val kotlinExtensions = "kotlin-android-extensions"
    //Safe args
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
    //maven
    const val maven = "com.github.dcendents.android-maven"
}

object Libs {
    private object Versions {
        const val appCompat = "1.0.2"
        const val lifecycle = "2.0.0"
        const val rxJava = "2.2.9"
        const val rxKotlin = "2.3.0"
        const val rxAndroid = "2.1.1"
        const val rxBinding = "3.0.0-alpha2"
        const val navigation = "2.1.0-alpha05"
        const val recyclerView = "1.1.0-alpha06"
        const val retrofit = "2.6.0"
        const val okhttp3Logging = "3.14.2"
        const val koin = "2.0.1"
    }

    //Kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Android.V.kotlin}"
    //AppCompat
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    //ViewModel + LiveData
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    //RxJava2
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    //RxKotlin
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    //RxAndroid
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    //RxBinding
    const val rxBinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"
    //Navigation component
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUI = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    //RecyclerView
    const val recycler = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val recyclerSelection = "androidx.recyclerview:recyclerview-selection:${Versions.recyclerView}"
    //Retrofit 2
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    //Okhttp3 logging
    const val htttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Logging}"
    //Koin
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinVM = "org.koin:koin-android-viewmodel:${Versions.koin}"
    const val koinScope = "org.koin:koin-android-scope:${Versions.koin}"

}