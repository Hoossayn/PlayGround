import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.example.playground.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}



dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.com.google.devtools.ksp.gradlePlugin)
}

tasks.check {
    dependsOn("ktlintCheck", "jacocoTestReport")
}

gradlePlugin {
    plugins {
        register("androidApp") {
            id = "com.example.playground.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidHilt") {
            id = "com.example.playground.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibrary") {
            id = "com.example.playground.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "com.example.playground.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidTest") {
            id = "com.example.playground.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }

        register("kotlinJVM") {
            id = "com.example.playground.kotlin.jvm"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("detekt") {
            id = "com.example.playground.build.detekt"
            implementationClass = "DetektConventionPlugin"
        }

        register("ktlint") {
            id = "com.example.playground.build.ktlint"
            implementationClass = "KtLintConventionPlugin"
        }

        register("androidNavigation") {
            id = "com.example.playground.android.navigation"
            implementationClass = "AndroidNavigationConventionPlugin"
        }

        register("androidAppCompose") {
            id = "com.example.playground.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "com.example.playground.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

    }
}