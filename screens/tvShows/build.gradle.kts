plugins {
    id("com.example.playground.android.feature")
    id("com.example.playground.android.library.compose")
    id("com.example.playground.android.navigation")
    id("com.example.playground.android.hilt")
}

android {
    namespace = "com.example.screens.tvshows"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {

    implementation(projects.core.data)
    implementation(projects.core.network)
    implementation(libs.androidx.core.ktx)

    // lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.io.coil.compose)
    implementation(libs.com.valentinilk.compose.shimmer)

    implementation(libs.androidx.palette)
    implementation(libs.androidx.palette.ktx)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    testImplementation(project(":ui-test-hilt-manifest"))

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(projects.core.test)
    androidTestImplementation(projects.core.test)
}
