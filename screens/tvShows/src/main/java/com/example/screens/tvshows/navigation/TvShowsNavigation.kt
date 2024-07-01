package com.example.screens.tvshows.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.ui.navigation.enterTransition
import com.example.core.ui.navigation.exitTransition
import com.example.core.ui.navigation.popEnterTransition
import com.example.core.ui.navigation.popExitTransition
import com.example.screens.tvshows.ui.TvShowsScreen

@Suppress("TopLevelPropertyNaming", "ktlint:standard:property-naming")
const val tvShowsNavPattern = "/tv-shows"

fun NavGraphBuilder.tvShowsScreen(onTVShowClick: (Long) -> Unit) {
    composable(
        route = tvShowsNavPattern,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        TvShowsScreen(onTVShowClick = onTVShowClick)
    }
}
