package com.ht117.config

object Deps {
    object App {
        const val id = "com.ht117.oddler"
        const val compileSdk = 33
        const val minSdk = 24
        const val targetSdk = 33
        const val code = 1
        const val name = "1.0"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.9.0"
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:2023.01.00"
        const val material = "androidx.compose.material3:material3"
        const val ui = "androidx.compose.ui:ui"
        const val graphics = "androidx.compose.ui:ui-graphics"

        const val uiTooling = "androidx.compose.ui:ui-tooling:1.3.3"
        const val uiPreview = "androidx.compose.ui:ui-tooling-preview"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0"
        const val activity = "androidx.activity:activity-compose:1.6.1"
        const val navigation = "androidx.navigation:navigation-compose:2.5.3"
        const val lifecycleRT = "androidx.lifecycle:lifecycle-runtime-compose:2.6.0"
    }

    object Common {
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
        const val koinCore = "io.insert-koin:koin-core:3.2.2"
        const val koinAndroid = "io.insert-koin:koin-android:3.2.2"
        const val koinCompose = "io.insert-koin:koin-androidx-compose:3.4.2"
    }

    object Network {
        const val ktorCore = "io.ktor:ktor-client-core:2.1.3"
        const val ktorOkhttp = "io.ktor:ktor-client-okhttp:2.1.3"
        const val serializeJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"
        const val ktorContent = "io.ktor:ktor-client-content-negotiation:2.1.3"
        const val ktorJson = "io.ktor:ktor-serialization-kotlinx-json:2.1.3"
        const val coilCompose = "io.coil-kt:coil-compose:2.2.2"
    }

    object Test {
        const val jUnit = "junit:junit:4.13.2"
        const val extJunit = "androidx.test.ext:junit:1.1.5"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
        const val jUnitCompose = "androidx.compose.ui:ui-test-junit4"
        const val composeBom = "androidx.compose:compose-bom:2022.10.00"
        const val composeManifest = "androidx.compose.ui:ui-test-manifest"
    }
}
