plugins {
    id("com.android.library")
    id("kotlin-android")
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
    implementation(Compose.hiltCompose)
}