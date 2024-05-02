package com.example.convention

import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun DependencyHandler.implementation(provider: Any) {
    add("implementation", provider)
}

internal fun DependencyHandler.debugImplementation(provider: Any) {
    add("debugImplementation", provider)
}

internal fun DependencyHandler.testImplementation(provider: Any) {
    add("testImplementation", provider)
}

internal fun DependencyHandler.androidTestImplementation(provider: Any) {
    add("androidTestImplementation", provider)
}

internal fun DependencyHandler.detekt(provider: Any) {
    add("detektPlugins", provider)
}

internal fun DependencyHandler.kapt(provider: Any) {
    add("kapt", provider)
}

internal fun DependencyHandler.ksp(provider: Any) {
    add("ksp", provider)
}

internal fun DependencyHandler.kaptTest(provider: Any) {
    add("kaptTest", provider)
}

internal fun DependencyHandler.kaptAndroidTest(provider: Any) {
    add("kaptAndroidTest", provider)
}
