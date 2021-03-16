package com.zzq.buildsrc

object BuildVersions {
    const val minSdkVersion = 14
    const val compileSdkVersion = 30
    const val targetSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
}

object DependTest {
    const val junit = "junit:junit:${Versions.test_junit}"
    const val ext_junit = "androidx.test.ext:junit:${Versions.test_junit_ext}"
    const val runner = "androidx.test:runner:${Versions.test_runner}"
    const val rules = "androidx.test:rules:${Versions.test_rules}"
}

object Espresso {
    const val core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val intents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"
    const val stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_version}"
    const val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin_version}"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
    const val plugin_android_extension = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin_version}"
}

object KotlinCoroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines}"
}

object Androidx {
    const val material = "com.google.android.material:material:${Versions.material}"

    const val app_compat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val documentFile = "androidx.documentfile:documentfile:${Versions.documentFile}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotations}"

    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.recyclerview}"
}

object Retrofit {
    const val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val rxjava = "com.squareup.retrofit2:adapter-rxjava:${Versions.retrofit}"
    const val mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
    const val rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val testing = "androidx.room:room-testing:${Versions.room}"
}

object Work {
    const val runtime = "androidx.work:work-runtime:${Versions.work}"
    const val testing = "androidx.work:work-testing:${Versions.work}"
    const val firebase = "androidx.work:work-firebase:${Versions.work}"
    const val runtime_ktx = "androidx.work:work-runtime-ktx:${Versions.work}"
}

object Navigation {
    const val runtime = "androidx.navigation:navigation-runtime:${Versions.navigation}"
    const val runtime_ktx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val fragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val testing = "androidx.navigation:navigation-testing:${Versions.navigation}"
    const val ui = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
}

object DataStore {
    //偏好设置DataStore
    const val preferencesDataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"

    // optional - RxJava2 support
    const val preferencesDataStoreRxJava2 = "androidx.datastore:datastore-preferences-rxjava2:${Versions.dataStore}"

    // optional - RxJava3 support
    const val preferencesDataStoreRxJava3 = "androidx.datastore:datastore-preferences-rxjava3:${Versions.dataStore}"


    //类型存储----------------------------------------------------------------------------------
    const val typeDataStore = "androidx.datastore:datastore:${Versions.dataStore}"

    // optional - RxJava2 support
    const val typeDataStoreRxJava2 = "androidx.datastore:datastore-rxjava2:${Versions.dataStore}"

    // optional - RxJava3 support
    const val typeDataStoreRxJava3 = "androidx.datastore:datastore-rxjava3:${Versions.dataStore}"
}

object Hilt {
    const val classPath = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val runTime =  "com.google.dagger:hilt-android:${Versions.hilt}"
    const val kapt =  "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
}

object Extension {
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    const val runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    //ViewModel
    const val viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    //LiveData
    const val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

    //Saved state module for ViewModel
    const val viewmodel_savedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"

    //alternately - if using Java8, use the following instead of lifecycle-compiler
    const val common_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    //optional - helpers for implementing LifecycleOwner in a Service
    const val service = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"

    //optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    //lifecycle-extensions在2.2.0就被弃用，将使用lifecycle-process
    const val process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"

    //optional - Test helpers for LiveData
    const val core_testing = "androidx.arch.core:core-testing:${Versions.arch_core}"
}

object Ktx {
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
}

object Depend {
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
}