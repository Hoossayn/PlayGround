package com.example.playground.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.screens.moviedetails.navigation.movieDetailsScreen
import com.example.screens.moviedetails.navigation.navigateToMovieDetails
import com.example.screens.movies.navigation.moviesRoutePattern
import com.example.screens.movies.navigation.moviesScreen
import com.example.screens.people.navigation.peopleScreen
import com.example.screens.settings.navigation.settingsScreen
import com.example.screens.tvshows.navigation.tvShowsScreen

@Suppress("UnusedPrivateMember")
@Composable
fun PlayGroundNavHost(
    navController: NavHostController,
    onShowSnackBar: suspend (String, String?) -> Boolean,
) {
    val onNavigateUp: () -> Unit = {
        navController.navigateUp()
    }

    val onMovieClick: (Long) -> Unit = { movieId ->
        navController.navigateToMovieDetails(
            movieId = movieId,
            navOptions = navOptions {},
        )
    }

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = moviesRoutePattern,
    ) {
        moviesScreen(onMovieClick = onMovieClick)
        tvShowsScreen(onTVShowClick = {})
        peopleScreen(onPersonClick = {})
        movieDetailsScreen(
            onMovieItemClick = onMovieClick,
            onNavigateUp = onNavigateUp,
            onWatchTrailerClick = {},
        )
        settingsScreen(onLoginClick = {})
    }
}
