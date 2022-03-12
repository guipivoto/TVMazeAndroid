plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = App.compileSdk

    defaultConfig {
        applicationId = "com.jobsity.challenge"
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        versionCode = App.targetSdk
        versionName = App.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.version
    }

    kotlinOptions {
        jvmTarget = Kotlin.jvmTarget
    }

     packagingOptions {
         resources {
             excludes.add("/META-INF/{AL2.0,LGPL2.1}")
         }
     }
}

dependencies {
    // Android X
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core)

    // Compose
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.hiltCompose)
    implementation(Compose.tooling)

    // Dagger
    implementation(Dagger.hiltAndroid)
    kapt(Dagger.hiltAndroidCompiler)

    // Internal
    implementation(project(":common:resources"))
    implementation(project(":common:theme"))

    // Tests
    testImplementation(Tests.junit)
    androidTestImplementation(Tests.testRunner)
}