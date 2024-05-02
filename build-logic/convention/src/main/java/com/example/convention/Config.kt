

@file:Suppress("ktlint:standard:property-naming")
package com.example.convention

object Config {
    const val compileSdkVersion = 34
    const val minimumSdkVersion = 26
    const val targetSdkVersion = 34
    const val versionCode = 1
    const val versionName = "0.0.1"
    const val isDebuggable = false // Toggle to generate a debuggable build

    // Lint
    val lintDisable: Set<String> = mutableSetOf("TypographyFractions", "TypographyQuotes")
    val lintEnable: Set<String> = mutableSetOf("RtlHardcoded", "RtlCompat", "RtlEnabled")
    val lintCheckOnly: Set<String> = mutableSetOf("NewApi", "InlinedApi")
}
