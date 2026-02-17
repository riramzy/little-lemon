# Little Lemon

A modern, feature-rich Android application for restaurant ordering, built with Kotlin and Jetpack Compose. This project demonstrates professional Android development practices with offline-first capabilities, local data persistence, and secure authentication.

## Table of contents
- [Overview](#overview)
- [Features](#features)
- [Technical Requirements](#technical-requirements)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Setup & Installation](#setup--installation)
- [Key Implementation Details](#key-implementation-details)
- [Resources](#resources)

## Overview

Little Lemon is a production-ready Android application designed with a focus on:

- **Offline-First Architecture**: Full functionality without internet connectivity using local database
- **Clean Architecture**: Separation of concerns with repository pattern and layered design
- **Modern UI**: Material Design 3 implementation with Jetpack Compose
- **Robust Networking**: Type-safe API communication with Ktor client and Kotlin serialization

The application enables users to browse menu items, search for dishes, manage their profile, and place orders with persistent local storage.

## Features

- **User Profile Management**: Persistent user data and preferences storage
- **Menu Browsing**: View and search menu items with filtering capabilities
- **Order Management**: Create, read, update, and delete orders locally
- **Offline Support**: Full app functionality with offline-first database-backed approach
- **Responsive UI**: Custom Material Design 3 theme with professional styling
- **Image Loading**: Efficient image caching and loading with Coil
- **Type-Safe Navigation**: Route-based navigation using sealed classes
- **Modern Styling**: Custom typography, color schemes, and animations

## Technical Requirements

| Requirement              | Version                   |
|--------------------------|---------------------------|
| **Java Development Kit** | 17+                       |
| **Kotlin**               | 1.9.0+                    |
| **Gradle**               | Latest (wrapper included) |
| **Android Studio**       | Latest stable version     |
| **Target SDK**           | API 34                    |
| **Minimum SDK**          | API 24                    |
| **Compile SDK**          | API 34                    |

## Architecture
The architecture of the Little Lemon application follows the MVVM (Model-View-ViewModel) pattern, providing a clear separation of concerns and facilitating easier testing and maintenance.

### Design Pattern

Little Lemon follows a **layered architecture** with clear separation of concerns:

```
┌─────────────────────────────────────┐
│  Presentation Layer (Compose UI)    │
├─────────────────────────────────────┤
│  ViewModel & State Management       │
├─────────────────────────────────────┤
│  Repository Layer (Data Abstraction)│
├─────────────────────────────────────┤
│  Data Sources (Local & Remote)      │
│  ├─ Room Database (DAOs)            │
│  ├─ Ktor HTTP Client                │
│  └─ SharedPreferences (UserData)    │
└─────────────────────────────────────┘
```
### Key Architectural Decisions

1. **Repository Pattern**: Repositories abstract data sources and provide a clean API for ViewModels
2. **Offline-First Approach**: Local Room database serves as the source of truth
3. **Reactive Streams**: Kotlin Flow for continuous data observation
4. **Type Safety**: Kotlin Coroutines for asynchronous operations
5. **Code Generation**: KSP (Kotlin Symbol Processing) for Room entity compilation

## Project Structure
- **/app:** Contains application code
    - **/data:** Data layer (Repositories, Models)
    - **/ui:** User interface layer (Activities, Fragments, Adapters)
    - **/di:** Dependency Injection components
    - **/utils:** Utility functions and classes
    - **/theme:** Custom Material Design 3 theme
  
## Technology Stack

### Core Android & Kotlin
- **Kotlin**: 1.9.0
- **Android Core**: androidx.core:core-ktx
- **Lifecycle Management**:
    - androidx.lifecycle:lifecycle-runtime
    - androidx.lifecycle:lifecycle-viewmodel
    - androidx.lifecycle:lifecycle-viewmodel-compose
    - androidx.lifecycle:lifecycle-livedata

### UI Framework
- **Jetpack Compose**: Material 3 with BOM management
- **Material Design 3**: Latest components and theming
- **Material Icons**: Core and extended icon sets (1.6.8)
- **Activity Compose**: Integration with Compose

### Data Persistence
- **Room Database**: Local data storage with type safety
    - Runtime & KTX extensions
    - Compiler via KSP (Kotlin Symbol Processing)
    - DAOs with Flow-based reactive queries

### Networking
- **Ktor Client**:
    - Core client for HTTP requests
    - Android-specific implementation
    - Content negotiation for automatic serialization
    - Built-in logging capabilities
- **Kotlinx Serialization**: JSON serialization/deserialization with compile-time safety

### Image Loading
- **Coil**: Modern, lightweight image loading library (2.7.0)
- Compose integration with automatic caching

### Navigation
- **Androidx Navigation Compose**: Type-safe, composable navigation routing

### Dependency Injection
- **Hilt**: Hilt is used for dependency injection throughout the application, ensuring that components are easily managed and providing a clean structure for our classes.

## Setup & Installation

### Prerequisites
- Android Studio (latest stable version)
- JDK 17 installed and configured
- An Android device or emulator with API 24 minimum

### Clone & Setup

```bash
# Clone the repository
git clone https://github.com/riramzy/little-lemon.git
cd little-lemon

# Open in Android Studio
# File → Open → Select little-lemon directory
```

### Build & Run

**Using Android Studio:**
1. Sync Gradle files (File → Sync Now)
2. Select device/emulator
3. Click Run (Shift + F10)

**Using Terminal:**

```bash
# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Build and run directly
./gradlew installDebug && adb shell am start -n com.example.littlelemon/.MainActivity
```

## Key Implementation Details

### Data Flow Pattern

1. **UI Layer** (Compose Screens)
    - Observes LiveData or StateFlow from ViewModels
    - Handles user interactions and navigation

2. **ViewModel Layer**
    - Manages UI state and business logic
    - Calls repository methods to fetch/modify data
    - Survives configuration changes

3. **Repository Layer**
    - Abstracts data sources (local + remote)
    - Provides clean API for ViewModels
    - Handles data validation and transformation

4. **Data Sources**
    - **Local**: Room DAOs with Flow-based reactive queries
    - **Remote**: Ktor client for API communication
    - **Preferences**: UserPreferences for simple key-value storage

### Offline-First Strategy

- Room database serves as the source of truth
- Network requests populate/sync local cache
- UI always reads from local database
- Changes persist immediately to database
- Background sync reconciles with server when online

## Resources

### Repository Links
- **GitHub**: [riramzy/little-lemon](https://github.com/riramzy/little-lemon)

### Key Source Files
- [Navigation Routes](https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/utils/Screen.kt)
- [MainActivity](https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/MainActivity.kt)
- [User Repository](https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/data/repos/UserRepo.kt)
- [Orders Repository](https://github.com/riramzy/little-lemon/blob/main/app/src/main/java/com/example/littlelemon/data/repos/OrdersRepo.kt)

### Documentation References
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [Ktor Client Documentation](https://ktor.io/docs/client.html)
- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Android Architecture Components](https://developer.android.com/topic/architecture)

---