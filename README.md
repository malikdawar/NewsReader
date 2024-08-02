# News Article Reader

The **News Article Reader** is an Android application designed to provide users with a seamless news-reading experience. This application fetches the latest news articles using https://newsapi.org/ API. The project is built using modern Android development practices, including Kotlin, Jetpack Compose, Kotlin Coroutines, and Retrofit, following the MVVM architecture pattern.

## Features

- **Filter News Articles:** API provides three type of news feeds as per the filter "relevancy", "popularity", "publishedAt", can be appliied using the filter in toolbar
- **Speech-to-Text Integration:** Speech to text API helps to record the voice and convert it to text. Press the button in the botton and say "reload" and see teh magic.
- **Clean Architecture:** Built with a single activity architecture following the MVVM pattern combined with UseCase and Repository layers.
- **Reactive UI:** The UI is implemented using Jetpack Compose, providing a reactive and declarative UI experience.
- **Asynchronous Operations:** Leveraging Kotlin Coroutines and Flows for efficient and non-blocking data handling.

## Technologies Used

- **Kotlin**: Programming language used for the entire project.
- **Jetpack Compose**: Modern Android toolkit for building native UI.
- **Kotlin Coroutines & Flows**: For handling asynchronous operations and streams of data.
- **Retrofit**: HTTP client for making network requests.
- **MVVM Architecture**: Separates the business logic and UI, facilitating easier testing and maintainability.
- **UseCase Pattern**: Encapsulates business logic and makes it reusable and testable.
- **Repository Pattern**: Abstracts data sources, allowing easy integration of new data providers or testing.

## Getting Started

### Prerequisites

- **Android Studio**: Make sure you have the latest version installed.
- **Kotlin**: Ensure your project is using Kotlin 1.8 or above.
- **Gradle**: Ensure you have the required Gradle version specified in the project.

### Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/malikdawar/NewsReader.git
   ```

2. **Open the project in Android Studio**:
   - Open Android Studio.
   - Click on `File` -> `Open` and navigate to the cloned repository.

3. **Sync the project**:
   - Let Android Studio sync the project and download the necessary dependencies.

4. **Build and Run**:
   - Connect an Android device or use an emulator.
   - Click on `Run` -> `Run app`.

### Usage

- On launch, the app will display a list of the latest news articles.
- Select any article to read it in details.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- **Jetpack Compose**: Thanks to the Android team for providing modern tools to build UIs.
- **Retrofit**: Simplifies the process of making HTTP requests.
- **Kotlin Coroutines**: For providing a simple way to write asynchronous code.
- **Android Speech-to-Text**: Enables the app to listen the user's commands.

