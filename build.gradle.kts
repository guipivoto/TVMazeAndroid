allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Gradle.plugin)
        classpath(Kotlin.plugin)
        classpath(Dagger.plugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}