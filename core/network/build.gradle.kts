plugins {
    id("com.example.playground.android.library")
    id("com.example.playground.android.hilt")
    id("com.example.playground.android.navigation")

    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    namespace = "com.example.core.network"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "TMDB_BASE_URL",
                "\"https://api.themoviedb.org/\"",
            )
        }

        release {
            buildConfigField(
                "String",
                "TMDB_BASE_URL",
                "\"https://api.themoviedb.org/\"",
            )

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(projects.core.common)
    implementation(projects.core.model)

    // retrofit
    api(libs.com.squareup.retrofit2)
    implementation(libs.com.google.code.gson)
    implementation(libs.com.squareup.retrofit2.converter.gson)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)

    // util
    debugImplementation(libs.com.github.chuckerteam.chucker.library)
    releaseImplementation(libs.com.github.chuckerteam.chucker.library.no.op)

    testImplementation(projects.core.test)
    testImplementation(libs.junit4)
    testImplementation(libs.com.squareup.okhttp3.mockwebserver)
    testImplementation(libs.io.mockk.android)
    testImplementation(libs.io.mockk.agent)

    androidTestImplementation(projects.core.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"
}
