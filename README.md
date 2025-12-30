# Little Lemon

A Kotlin-based Android application implemented with Jetpack Compose.  

Table of contents
- [About](#about)
- [Quick features](#quick-features)
- [Requirements](#requirements)
- [Project architecture (from repository)](#project-architecture-from-repository)
- [How the pieces interact (high level)](#how-the-pieces-interact-high-level)
- [APIs, SDKs & Libraries](#apis-sdks--libraries)
- [Project structure (package map with key files)](#project-structure-package-map-with-key-files)
- [Getting started — Build & Run](#getting-started---build--run)
- [Testing](#testing)
- [Missing items & next steps I can take](#missing-items--next-steps-i-can-take)
- [Contact & links](#contact--links)

## About
Little Lemon is an Android app written in Kotlin using Jetpack Compose for the UI. The app is designed with local persistence (Room), offline-first capabilities (local DAOs + repositories), Ktor for network calls and Firebase authentication integrations for sign-in flows.

## Quick features
- Compose-based UI and custom theme (fonts, colors, typography)
- Local persistence with Room (DAOs, local entities)
- Repository layer for data access (UserRepo, OrdersRepo, SearchRepo)
- Ktor client + Kotlinx Serialization for networking
- Firebase Authentication + Google Sign-in
- Image loading with Coil
- Navigation defined via sealed Screen routes

## Requirements
- JDK 17 (project targets Java 17 / Kotlin jvmTarget = 17)
- Android Studio (recommended)
- Android SDK for API 34 (compileSdk = 34)
- Device/emulator min API 24 (minSdk = 24)
- Gradle (project uses Gradle wrapper)

## Project architecture
The project follows a layered app architecture (Repository + local data + UI). The package structure and concrete files scanned in the repo show the following layout and responsibilities:

- com.example.littlelemon
    - MainActivity.kt — app entry point (sets Compose content)
    - LittleLemonApp (Composable entry, referenced by MainActivity)
- com.example.littlelemon.utils
    - Screen.kt — sealed class listing navigation routes used by Navigation Compose
- com.example.littlelemon.ui.theme
    - Color.kt — color tokens
    - Type.kt — font families and Typography
    - (theme provider composables)
- com.example.littlelemon.data
    - local (database and DAOs)
        - local order entities and dao types referenced (LocalOrder, LocalOrderItem, LocalOrderWithItems, LocalOrdersDao)
    - repos (repository layer)
        - UserRepo.kt — manages user session, registration, profile persistence using UserPreferences
        - OrdersRepo.kt — local orders CRUD & queries (wraps LocalOrdersDao)
        - SearchRepo.kt — search and list retrieval via LocalSearchDao
    - preferences
        - UserPreferences (referenced by UserRepo) — local key/value storage for user data / onboarding
- resources
    - drawables, vector assets, fonts (Karla, Markazi) used by theme

Key architectural decisions visible in code:
- Repository pattern: Repos provide a clear API for ViewModels / UI to interact with data sources.
- Local-first data: Room + DAOs for persistence (KSP used for Room compiler).
- Compose navigation: routes centralized in `Screen.kt`.
- The code uses Kotlin Coroutines / Flow (e.g., OrdersRepo exposes Flow<List<LocalOrder>>).
- No DI framework code was found in the scanned files (no explicit Hilt/Koin/Dagger files detected). If you use DI, it may be in unscanned files or omitted.

## How the pieces interact (high level)
1. UI (Compose screens) read and observe data provided by ViewModels.
2. ViewModels call repository methods to get data and perform actions.
3. Repositories delegate to local DAOs (Room) or network clients (Ktor) and to preferences for simple user storage.
4. Room entities and DAOs handle local persistence; KSP is used to generate Room boilerplate.
5. Networking serialization is handled by kotlinx.serialization with Ktor content negotiation.
6. Firebase Auth / Google Sign-In provide authentication flows and tokens for API calls (if applicable).

## APIs, SDKs & Libraries
Extracted from `app/build.gradle.kts` and top-level build file. Some dependencies are referenced via the Gradle Version Catalog (`libs.*`) — their exact versions live in the catalog file (not yet scanned). The following list contains all libraries and plugins discovered in the build files:

Build plugins (top-level)
- org.jetbrains.kotlin.jvm version 1.9.0 (top-level reference)
- com.google.gms:google-services version 4.4.4 (top-level reference)
- Gradle catalog aliases used: `libs.plugins.android.application`, `libs.plugins.kotlin.android`, `libs.plugins.kotlin.compose`, `libs.plugins.kotlin.serialization`, `libs.plugins.ksp`

Core Android & Kotlin
- Kotlin Standard Library (via Kotlin plugin)
- androidx.core:core-ktx (alias: libs.androidx.core.ktx)
- AndroidX Lifecycle:
    - lifecycle-runtime (libs.androidx.lifecycle.runtime)
    - lifecycle-viewmodel (libs.androidx.lifecycle.viewmodel)
    - lifecycle-viewmodel-compose (libs.androidx.lifecycle.viewmodel.compose)
    - lifecycle-livedata (libs.androidx.lifecycle.livedata)

Jetpack Compose
- androidx.activity:activity-compose (libs.androidx.activity.compose)
- Compose BOM (platform(libs.androidx.compose.bom))
- androidx.compose.ui, graphics, tooling-preview (libs.androidx.compose.ui, libs.androidx.compose.ui.graphics, libs.androidx.compose.ui.tooling.preview)
- Material3 (libs.androidx.compose.material3)
- Debug tooling (compose ui tooling, ui test manifest)
- Material icons (explicit versions found):
    - androidx.compose.material:material-icons-core:1.6.8
    - androidx.compose.material:material-icons-extended:1.6.8

Navigation
- androidx.navigation:navigation-compose (libs.androidx.navigation.compose)

Persistence
- Room runtime & ktx (libs.androidx.room.runtime, libs.androidx.room.ktx)
- Room compiler via KSP: ksp(libs.androidx.room.compiler)
- KSP plugin referenced (`libs.plugins.ksp`) is enabled in the app module

Networking & Serialization
- Ktor client:
    - ktor-client-core (libs.ktor.client.core)
    - ktor-client-android (libs.ktor.client.android)
    - ktor-client-content-negotiation (libs.ktor.client.content.negotiation)
    - ktor-serialization-kotlinx-json (libs.ktor.serialization.kotlinx.json)
    - ktor-client-logging (libs.ktor.client.logging)
- Kotlinx Serialization: kotlinx-serialization-json (libs.kotlinx.serialization.json)
- Serialization plugin enabled (libs.plugins.kotlin.serialization)

Image Loading
- Coil Compose: io.coil-kt:coil-compose:2.7.0

Firebase & Google Sign-In
- com.google.firebase:firebase-auth:23.1.0
- com.google.android.gms:play-services-auth:21.2.0
- Google services Gradle plugin referenced (com.google.gms.google-services)

Testing
- JUnit (libs.junit)
- AndroidX JUnit (libs.androidx.junit)
- Espresso core (libs.androidx.espresso.core)
- Compose UI test JUnit4 (libs.androidx.compose.ui.test.junit4)
- Compose BOM used for androidTest as well

Other
- Kotlin JVM target: 17
- compileSdk: 34, targetSdk: 34, minSdk: 24

## Getting started — Build & Run
1. Clone the repo:
   ```
   git clone https://github.com/riramzy/little-lemon.git
   cd little-lemon
   ```
2. Open the project in Android Studio and let the IDE sync Gradle.
3. Add any required Firebase configuration (google-services.json) if you plan to test auth flows locally.
4. Build and run:
    - From Android Studio: Run on device/emulator.
    - From terminal:
      ```
      ./gradlew assembleDebug
      ./gradlew installDebug
      ```

## Testing
- Unit tests: `./gradlew test`
- Instrumented tests: `./gradlew connectedAndroidTest` (device/emulator)
- Compose UI tests available under androidTest (compose ui test junit4 dependency included)

## Contact & links
- Repository: https://github.com/riramzy/little-lemon
- Example key source files:
    - Screen routes: https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/utils/Screen.kt
    - MainActivity: https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/MainActivity.kt
    - UserRepo: https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/data/repos/UserRepo.kt
    - OrdersRepo: https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/data/repos/OrdersRepo.kt

