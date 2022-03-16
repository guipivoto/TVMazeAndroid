plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {

    compileSdk = App.compileSdk

    defaultConfig {
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Kotlin.jvmTarget
    }
}

dependencies {

    // Coroutines
    implementation(Kotlin.coroutinesAndroid)

    // Hilt
    implementation(Dagger.hiltAndroid)
    kapt(Dagger.hiltAndroidCompiler)

    // Internal
    implementation(project(":services:restapi"))

    // Tests
    testImplementation(Tests.junit)
    testImplementation(Tests.mockito)
    testImplementation(Tests.coroutines)
    androidTestImplementation(Tests.testRunner)
}