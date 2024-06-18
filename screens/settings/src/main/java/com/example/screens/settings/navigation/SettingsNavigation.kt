package com.example.screens.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.ui.navigation.enterTransition
import com.example.core.ui.navigation.exitTransition
import com.example.core.ui.navigation.popEnterTransition
import com.example.core.ui.navigation.popExitTransition
import com.example.screens.settings.ui.SettingsScreen

const val settingsNavPattern = "/settings"

fun NavGraphBuilder.settingsScreen(onLoginClick: () -> Unit) {
    composable(
        route = settingsNavPattern,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        SettingsScreen(onLoginClick = onLoginClick)
    }
}
