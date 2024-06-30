package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure Compose specific options.
 */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *>) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("composeCompiler").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()

            implementation(platform(bom))
            implementation(libs.findLibrary("androidx-compose-ui").get())
            implementation(libs.findLibrary("androidx-compose-ui-graphics").get())
            implementation(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            implementation(libs.findLibrary("androidx-compose-material3").get())
            implementation(libs.findLibrary("androidx-compose-material-icons-extended").get())

            androidTestImplementation(platform(bom))
            androidTestImplementation(libs.findLibrary("androidx-compose-ui").get())
            androidTestImplementation(libs.findLibrary("androidx-compose-ui-graphics").get())
            androidTestImplementation(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            androidTestImplementation(libs.findLibrary("androidx-compose-material3").get())
            androidTestImplementation(
                libs.findLibrary("androidx-compose-material-icons-extended").get(),
            )
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.addAll(buildComposeMetricsParameters())
        }
    }
}

private fun Project.buildComposeMetricsParameters(): List<String> {
    val metricParameters = mutableListOf<String>()
    val enableMetricsProvider = project.providers.gradleProperty("enableComposeCompilerMetrics")
    val enableMetrics = (enableMetricsProvider.orNull == "true")
    if (enableMetrics) {
        val metricsFolder = layout.buildDirectory.file("compose-metrics")
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                metricsFolder.get().asFile.absolutePath,
        )
    }

    val enableReportsProvider = project.providers.gradleProperty("enableComposeCompilerReports")
    val enableReports = (enableReportsProvider.orNull == "true")
    if (enableReports) {
        val reportsFolder = layout.buildDirectory.file("compose-reports")
        metricParameters.add("-P")
        metricParameters.add(
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                reportsFolder.get().asFile.absolutePath,
        )
    }
    return metricParameters.toList()
}
