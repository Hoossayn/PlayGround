package com.example.movies.navigation

import android.os.Build
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.example.screens.movies.navigation.moviesRoutePattern
import com.example.screens.movies.navigation.moviesScreen
import com.example.screens.movies.screen.MOVIES_GRID_ITEMS_TEST_TAG
import com.example.uitesthiltmanifest.HiltComponentActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(AndroidJUnit4::class)
@Config(
    application = HiltTestApplication::class,
    sdk = [Build.VERSION_CODES.O],
    instrumentedPackages = ["androidx.loader.content"],
    qualifiers = "xlarge",
)
@HiltAndroidTest
class NavigationTest {

    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * Create a temporary folder used to create a Data Store file. This guarantees that
     * the file is removed in between each test, preventing a crash.
     */
    @BindValue
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    /**
     * Use the primary activity to initialize the app normally.
     */
    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private var onMovieClick = 0

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
        hiltRule.inject()
    }

    @Test
    fun moviesScreen_onLoad_addMoviesScreenToNavHost() {
        composeTestRule.apply{
            setContent {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())

                NavHost(
                    navController = navController,
                    startDestination = moviesRoutePattern
                ) {
                    moviesScreen(onMovieClick = {onMovieClick++})
                }
            }

            onNodeWithTag(MOVIES_GRID_ITEMS_TEST_TAG, useUnmergedTree = true)
                .onChildren()
                .onFirst()
                .performClick()

            assertThat(navController.currentDestination?.route).isEqualTo(moviesRoutePattern)
            assertThat(onMovieClick).isEqualTo(1)
        }
    }
}