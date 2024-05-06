package com.example.playground.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.playground.navigation.BottomNavigation
import com.example.playground.navigation.PlayGroundNavHost

const val MAIN_CONTENT_TEST_TAG = "mainContent"

@Composable
fun PlayGroundApp(
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onShowSnackBar: suspend (String, String?) -> Boolean = {msg, act ->
        snackBarHostState.showSnackbar(
            message = msg,
            actionLabel = act,
            duration = SnackbarDuration.Short,
        ) == SnackbarResult.ActionPerformed
    },
) {
    val bottomBarState = rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold (
        modifier = Modifier.testTag(MAIN_CONTENT_TEST_TAG),
        bottomBar = { BottomNavigation(navController, bottomBarState.value) },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ){ innerPadding ->

        Box (
            modifier = Modifier.padding(
                bottom = max(
                    a = 0.dp,
                    b = innerPadding.calculateBottomPadding() - 40.dp,
                )
            ),
            content = {
                PlayGroundNavHost(
                    navController = navController,
                    onShowSnackBar = onShowSnackBar,
                )
            }
        )
    }
    /*LaunchedEffect(navBackStackEntry?.destination?.route) {
        when (navBackStackEntry?.destination?.route) {
            movieDetailsRoutePattern, peopleDetailsRoutePattern, loginRoutePattern,
            mediaRoutePattern,
            -> bottomBarState.value = false

            else -> bottomBarState.value = true
        }
    }*/
}














