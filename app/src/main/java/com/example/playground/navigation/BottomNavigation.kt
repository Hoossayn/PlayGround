package com.example.playground.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme
import com.example.playground.R
import com.example.screens.movies.navigation.moviesRoutePattern
import com.example.screens.people.navigation.peopleNavPattern
import com.example.screens.settings.navigation.settingsNavPattern
import com.example.screens.tvshows.navigation.tvShowsNavPattern

const val BOTTOM_NAV_BAR_TEST_TAG = "BottomNavigationBar"

@Composable
@ThemePreviews
@ExcludeFromGeneratedCoverageReport
fun BottomNavigationPreview() {
    PlayGroundTheme {
        Row(modifier = Modifier.padding(PlayGround.spacing.two)) {
            BottomNavigation(navController = rememberNavController(), show = true)
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavHostController,
    show: Boolean = true,
) {
    val items = listOf(
        BottomNavItem.Movies,
        BottomNavItem.TVShows,
        BottomNavItem.People,
        BottomNavItem.More,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    AnimatedVisibility(
        visible = show,
        enter = slideInVertically() +
        expandVertically(expandFrom = Alignment.Top) +
        fadeIn(
            initialAlpha = 0.3f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ),
        exit = slideOutVertically() +
        shrinkVertically(shrinkTowards = Alignment.Bottom) + fadeOut(),
        content = {
            NavigationBar(modifier = Modifier.testTag(BOTTOM_NAV_BAR_TEST_TAG)) {
                items.forEach { item ->
                    addNavBarItem(
                        screen = item,
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(
                                route = item.route,
                                navOptions = navOptions {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            )
                        }
                    )
                }
            }
        }
    )
}

sealed interface BottomNavItem {
    val icon: Int
    val route: String

    @Composable
    fun title(): String

    data object Movies : BottomNavItem {
        override val icon: Int
            get() = R.drawable.baseline_video_library_24
        override val route: String
            get() = moviesRoutePattern

        @Composable
        override fun title(): String =
            stringResource(id = com.example.screens.movies.R.string.movies)
    }

    data object TVShows : BottomNavItem {
        override val icon: Int
            get() = R.drawable.baseline_live_tv_24
        override val route: String
            get() = tvShowsNavPattern

        @Composable
        override fun title(): String =
            stringResource(com.example.screens.tvshows.R.string.tv_shows_title)
    }

    data object People : BottomNavItem {
        override val icon: Int
            get() = R.drawable.baseline_people_24
        override val route: String
            get() = peopleNavPattern

        @Composable
        override fun title(): String = stringResource(com.example.screens.people.R.string.people)
    }

    data object More : BottomNavItem {
        override val icon: Int
            get() = R.drawable.baseline_more_24
        override val route: String
            get() = settingsNavPattern

        @Composable
        override fun title(): String =
            stringResource(com.example.screens.settings.R.string.settings)
    }
}

@Composable
private fun RowScope.addNavBarItem(
    screen: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        modifier = Modifier.testTag("${screen.title().lowercase().replace(" ", "_")}_nav"),
        label = { Text(text = screen.title()) },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = screen.title(),
                tint = PlayGround.color.secondary,
            )
        },
        selected = selected,
        alwaysShowLabel = true,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(),
    )
}
