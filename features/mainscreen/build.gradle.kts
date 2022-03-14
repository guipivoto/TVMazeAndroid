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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.version
    }
}

dependencies {
    // Internal
    implementation(project(":common:contract"))
    implementation(project(":common:theme"))
    implementation(project(":repository:tvshow"))

    // Compose
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.hiltCompose)
    implementation(Compose.toolingPreview)
    debugImplementation(Compose.tooling)

    // Hilt
    implementation(Dagger.hiltAndroid)
    kapt(Dagger.hiltAndroidCompiler)

    // External APIs
    implementation(API.coil)

    // Tests
    testImplementation(Tests.junit)
    androidTestImplementation(Tests.testRunner)
}