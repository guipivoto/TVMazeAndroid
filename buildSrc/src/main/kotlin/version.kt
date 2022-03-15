import org.gradle.api.JavaVersion

object App {
    const val compileSdk = 31
    const val minSdk = 28
    const val targetSdk = 31
    private const val majorVersion = 1
    private const val minorVersion = 0
    private const val patchVersion = 0
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"
}

object Gradle {
    private const val pluginVersion = "7.0.3"
    const val plugin = "com.android.tools.build:gradle:$pluginVersion"
}

object Java {
    val version = JavaVersion.VERSION_1_8
}

object Kotlin {
    private const val version = "1.6.10"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    const val jvmTarget = "1.8"

    private const val coroutineVersion = "1.5.2"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
}

object Dagger {
    private const val version = "2.39.1"
    const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    const val hiltAndroid = "com.google.dagger:hilt-android:$version"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version"
}

object Compose {
    @Suppress("MemberVisibilityCanBePrivate")
    const val version = "1.1.1"
    const val ui = "androidx.compose.ui:ui:$version"
    const val material = "androidx.compose.material:material:$version"
    const val tooling = "androidx.compose.ui:ui-tooling:$version"
    const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"

    private const val hiltVersion = "1.0.0"
    const val hiltCompose = "androidx.hilt:hilt-navigation-compose:$hiltVersion"
}

object Room {
    private const val version = "2.4.2"
    const val runtime = "androidx.room:room-runtime:$version"
    const val compiler = "androidx.room:room-compiler:$version"
}

object AndroidX {
    const val core = "androidx.core:core-ktx:1.7.0"
    const val appCompat = "androidx.appcompat:appcompat:1.3.1"
    const val biometrics = "androidx.biometric:biometric:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val crypto = "androidx.security:security-crypto:1.0.0"
}

object API {
    const val volley = "com.android.volley:volley:1.2.1"
    const val gson = "com.google.code.gson:gson:2.8.5"
    const val coil = "io.coil-kt:coil-compose:1.4.0"
}

object Tests {
    const val junit = "junit:junit:4.12"
    const val testRunner = "androidx.test.ext:junit:1.1.3"
}