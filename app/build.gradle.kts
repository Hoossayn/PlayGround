import com.example.convention.Config

plugins {
    id("com.example.playground.android.application")
    id("com.example.playground.android.hilt")
    id("com.example.playground.android.application.compose")
    id("com.example.playground.android.navigation")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.playground"

    defaultConfig {
        applicationId = "com.example.playground"
        minSdk = Config.minimumSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    implementation(projects.core.designSystem)
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.data)

    implementation(projects.screens.movies)
    implementation(projects.screens.tvShows)
    implementation(projects.screens.people)
    implementation(projects.screens.movieDetails)
    implementation(projects.screens.settings)

    // lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.com.google.android.material)

    // Performance
    implementation(libs.androidx.metrics.performance)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.org.jetbrains.kotlinx.coroutines.guava)

    // retrofit
    implementation(libs.com.squareup.retrofit2)
    implementation(libs.com.google.code.gson)
    implementation(libs.com.squareup.retrofit2.converter.gson)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.com.google.accompanist.systemuicontroller)

    testImplementation(projects.core.test)
    androidTestImplementation(projects.core.test)
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
}
