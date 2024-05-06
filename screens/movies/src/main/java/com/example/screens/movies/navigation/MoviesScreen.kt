package com.example.screens.movies.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.ui.navigation.enterTransition
import com.example.core.ui.navigation.exitTransition
import com.example.core.ui.navigation.popEnterTransition
import com.example.core.ui.navigation.popExitTransition

const val  moviesRoutePattern = "/movies"

fun NavGraphBuilder.moviesScreen(onMovieClick: (Long) -> Unit) {
    composable(
        route = moviesRoutePattern,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        //MoviesScreen(onMovieClick = onMovieClick)
    }
}