# Aedo

Aedo is a modern, cross-platform movie discovery application built with **Kotlin Multiplatform** and **Compose Multiplatform**. It allows users to explore popular and trending movies, filter by streaming services, and features an **AI-powered review summarization** tool to help users quickly get the gist of movie reviews.

## 🚀 Features

- **Movie Discovery:** Explore popular and trending movies updated in real-time.
- **Streaming Service Filters:** Find movies available on your favorite streaming platforms.
- **Detailed Movie Info:** View trailers, synopses, similar movies, and more.
- **AI Review Summarization:** Utilizes ML Kit and LiteRT to provide concise summaries of movie reviews.
- **Adaptive UI:** Built with Material 3 Adaptive Navigation for a seamless experience across mobile and tablets (List-Detail patterns).
- **Offline Support:** Local caching of movie data using Room.
- **Functional Architecture:** Employs Arrow-kt for robust error handling and functional programming patterns.

## 🛠 Tech Stack

- **Multiplatform:** [Kotlin Multiplatform (KMP)](https://kotlinlang.org/docs/multiplatform.html)
- **UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Android & iOS)
- **Navigation:** Material 3 Adaptive Navigation (Nav3)
- **Dependency Injection:** [Koin](https://insert-koin.io/)
- **Networking:** [Ktor](https://ktor.io/)
- **Persistence:** [Room](https://developer.android.com/training/data-storage/room) (Bundled SQLite)
- **Image Loading:** [Coil3](https://coil-kt.github.io/coil/)
- **Functional Programming:** [Arrow-kt](https://arrow-kt.io/)
- **API:** [TMDB API](https://www.themoviedb.org/documentation/api) (via tmdb-api library)
- **AI/ML:** ML Kit Summarization & LiteRT

## 📂 Project Structure

The project is highly modularized for better maintainability and separation of concerns:

- `:composeApp`: The main entry point containing the UI and shared application logic.
- `:models`: Shared data models used across all modules.
- `:data:movies`: Repository and data sources for movie-related data.
- `:core:persistence`: Database management and local storage using Room.
- `:core:summarizer`: AI summarization service logic.
- `:core:tvdbService`: Network service for fetching movie data.

## 🛠 Getting Started

### Prerequisites

- **JDK 17+**
- **Android Studio** (latest version) or **IntelliJ IDEA**
- **Xcode** (for running the iOS application)
- **TMDB API Key:** You'll need an API key from TMDB to fetch movie data.

### Build and Run

#### Android
```shell
./gradlew :composeApp:assembleDebug
```
Or use the run configuration in Android Studio.

#### iOS
Open the `iosApp` directory in Xcode and run the `iosApp` target, or use the Gradle task (if configured).

## 📄 License

This project is licensed under the **Apache License 2.0**. See the [LICENSE](LICENSE) file for more details.

---

*Aedo - Your intelligent movie companion.*
