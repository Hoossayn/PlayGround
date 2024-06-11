package com.example.screens.moviedetails.navigation

import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.uitesthiltmanifest.HiltComponentActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import com.example.core.ui.R
import com.example.screens.moviedetails.ui.MOVIE_DETAILS_RECOMMENDATIONS_ROW
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import kotlin.properties.ReadOnlyProperty

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

    private var onMovieItemClick = 0
    private var onNavigateUp = 0

    private lateinit var navController: TestNavHostController

    private fun AndroidComposeTestRule<*, *>.stringResource(
        @StringRes resId: Int,
    ) = ReadOnlyProperty<Any?, String> { _, _ -> activity.getString(resId) }

    // The strings used for matching in these tests
    private val navigateUp by composeTestRule.stringResource(R.string.navigate_up)

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
        hiltRule.inject()
    }

    @Test
    fun movieDetailsScreen_onLoad_addMovieDetailsScreenToNavHost() {
        composeTestRule.apply {
            setContent {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())

                NavHost(
                    navController = navController,
                    startDestination = movieDetailsRoutePattern
                ) {
                    movieDetailsScreen(
                        onMovieItemClick = { onMovieItemClick++},
                        onNavigateUp = { onNavigateUp++ }
                    )
                }
            }

            onNodeWithTag(MOVIE_DETAILS_RECOMMENDATIONS_ROW, useUnmergedTree = true)
                .onChildren()
                .onFirst()
                .performClick()
            onNodeWithContentDescription(navigateUp).performClick()

            assertThat(navController.currentDestination?.route).isEqualTo(movieDetailsRoutePattern)
            assertThat(onMovieItemClick).isEqualTo(1)
            assertThat(onNavigateUp).isEqualTo(1)
        }
    }
}