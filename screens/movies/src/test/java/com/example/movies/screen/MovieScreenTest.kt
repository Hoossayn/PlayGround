package com.example.movies.screen

import android.os.Build
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.Coil
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.model.Movie
import com.example.core.model.MovieListFilterItem
import com.example.core.test.util.fakeSuccessImageLoader
import com.example.core.test.util.testMovies
import com.example.core.ui.component.MOVIE_POSTER_TEST_TAG
import com.example.screens.movies.screen.APPEND_LOAD_PROGRESS_TEST_TAG
import com.example.screens.movies.screen.CHIP_GROUP_TEST_TAG
import com.example.screens.movies.screen.FRESH_LOAD_PROGRESS_TEST_TAG
import com.example.screens.movies.screen.MOVIES_GRID_ITEMS_TEST_TAG
import com.example.screens.movies.screen.MoviesScreen
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [Build.VERSION_CODES.O],
    instrumentedPackages = ["androidx.loader.content"],
    qualifiers = "xlarge",
)
class MovieScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var onMovieClickCounter = 0
    private var onFilterItemSelectedCounter = 0

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Coil.setImageLoader(fakeSuccessImageLoader)
        ShadowLog.stream = System.out
    }

    @Test
    fun movieScreen_moviesLoading_showLoadingProgress() {
        composeTestRule.setContent {
            MoviesScreen(
                filters = testFilters(),
                movieLazyPagingItems = flowOf(
                    PagingData.from(
                        data = emptyList<Movie>(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Loading,
                            append = LoadState.NotLoading(false),
                            prepend = LoadState.NotLoading(false)
                        )
                    )
                ).collectAsLazyPagingItems(),
                onMovieClick = { onMovieClickCounter++ },
                onFilterItemSelected = { onFilterItemSelectedCounter++ }
            )
        }

        composeTestRule.onNodeWithTag(FRESH_LOAD_PROGRESS_TEST_TAG)
            .assertExists()
            .assert(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun moviesScreen_appendMoviesLoading_showAppendLoadingProgress() {
        composeTestRule.setContent {
            MoviesScreen(
                filters = testFilters(),
                movieLazyPagingItems = flowOf(
                    PagingData.from(
                        data = emptyList<Movie>(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.NotLoading(false),
                            append = LoadState.Loading,
                            prepend = LoadState.NotLoading(false),
                        ),
                    ),
                ).collectAsLazyPagingItems(),
                onMovieClick = { onMovieClickCounter++ },
                onFilterItemSelected = { onFilterItemSelectedCounter++ },
            )
        }

        composeTestRule.onNodeWithTag(APPEND_LOAD_PROGRESS_TEST_TAG)
            .assertExists()
            .assert(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun moviesScreen_nowPlayingMoviesFilterSelected_nowPlayingMoviesShown() {
        composeTestRule.setContent {
            MoviesScreen(
                filters = testFilters(),
                movieLazyPagingItems = flowOf(
                    PagingData.from(data = testMovies()),
                ).collectAsLazyPagingItems(),
                onMovieClick = { onMovieClickCounter++ },
                onFilterItemSelected = { onFilterItemSelectedCounter++ },
            )
        }
        composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNode(hasText("Now Playing") and hasClickAction())
            .assertExists()
            .assertIsSelected()
            .assertIsDisplayed()
            .performClick()

        assertThat(onFilterItemSelectedCounter).isEqualTo(1)
    }

    @Test
    fun movieScreen_popularMoviesFilterSelected_popularMoviesShown() {
        composeTestRule.setContent {
            MoviesScreen(
                filters = testFilters().selectItem("Popular"),
                movieLazyPagingItems = flowOf(
                    PagingData.from(data = testMovies()),
                ).collectAsLazyPagingItems(),
                onMovieClick = { onMovieClickCounter++ },
                onFilterItemSelected = { onFilterItemSelectedCounter++ }
            )
        }

        composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
            .assertExists()
            .assert(hasScrollAction())
            .assertIsDisplayed()

        composeTestRule.onNode(hasText("Popular") and hasClickAction())
            .assertExists()
            .assertIsSelected()
            .assertIsDisplayed()
            .performClick()

        assertThat(onFilterItemSelectedCounter).isEqualTo(1)
    }

    @Test
    fun moviesScreen_topRatedMoviesFilterSelected_topRatedMoviesShown() {
        composeTestRule.setContent {
            MoviesScreen(
                filters = testFilters().selectItem("Top Rated"),
                movieLazyPagingItems = flowOf(
                    PagingData.from(data = testMovies()),
                ).collectAsLazyPagingItems(),
                onMovieClick = { onMovieClickCounter++ },
                onFilterItemSelected = { onFilterItemSelectedCounter++ },
            )
        }
        composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()
        with(composeTestRule.onNode(hasText("Top Rated") and hasClickAction())) {
            assertExists()
            assertIsSelected()
            assertIsDisplayed()
            performClick()
        }

        assertThat(onFilterItemSelectedCounter).isEqualTo(1)
    }

    @Test
    fun moviesScreen_upcomingMoviesFilterSelected_upcomingMoviesShown() {
        composeTestRule.setContent {
            MoviesScreen(
                filters = testFilters().selectItem("Upcoming"),
                movieLazyPagingItems = flowOf(
                    PagingData.from(data = testMovies()),
                ).collectAsLazyPagingItems(),
                onMovieClick = { onMovieClickCounter++ },
                onFilterItemSelected = { onFilterItemSelectedCounter++ },
            )
        }
        composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
            .assertExists()
            .assertIsDisplayed()
            .performTouchInput { swipeLeft() }

        with(composeTestRule.onNode(hasText("Upcoming") and hasClickAction())) {
            assertExists()
            assertIsSelected()
            assertIsDisplayed()
            performClick()
        }

        assertThat(onFilterItemSelectedCounter).isEqualTo(1)
    }

    @Test
    fun moviesScreen_movieItemClick_movieDetailsScreenShown() {
        composeTestRule.setContent {
            MoviesScreen(
                filters = testFilters(),
                movieLazyPagingItems = flowOf(
                    PagingData.from(data = testMovies()),
                ).collectAsLazyPagingItems(),
                onMovieClick = { onMovieClickCounter++ },
                onFilterItemSelected = { onFilterItemSelectedCounter++ },
            )
        }
        composeTestRule.onNodeWithTag(MOVIES_GRID_ITEMS_TEST_TAG, useUnmergedTree = true)
            .assertExists()
            .assertIsDisplayed()
            .assert(hasScrollAction())
            .onChildren()
            .assertCountEquals(4)

        composeTestRule.onAllNodesWithTag(MOVIE_POSTER_TEST_TAG, useUnmergedTree = true)
            .assertCountEquals(4)
            .onFirst()
            .assertHasClickAction()
            .performClick()

        assertThat(onMovieClickCounter).isEqualTo(1)
    }
}

private fun List<MovieListFilterItem>.selectItem(whereLabel: String) = map {
    if (labelFor(it) == whereLabel) {
        it.copy(isSelected = true)
    } else {
        it.copy(isSelected = false)
    }
}

private fun labelFor(filterItem: MovieListFilterItem): String {
    return when (filterItem.type) {
        MovieListFilterItem.FilterType.NOW_PLAYING -> "Now Playing"
        MovieListFilterItem.FilterType.POPULAR -> "Popular"
        MovieListFilterItem.FilterType.TOP_RATED -> "Top Rated"
        MovieListFilterItem.FilterType.UPCOMING -> "Upcoming"
        MovieListFilterItem.FilterType.DISCOVER -> "Discover"
    }
}

@ExcludeFromGeneratedCoverageReport
private fun testFilters() = listOf(
    MovieListFilterItem(
        isSelected = true,
        type = MovieListFilterItem.FilterType.NOW_PLAYING,
    ),
    MovieListFilterItem(
        isSelected = false,
        type = MovieListFilterItem.FilterType.POPULAR,
    ),
    MovieListFilterItem(
        isSelected = false,
        type = MovieListFilterItem.FilterType.TOP_RATED,
    ),
    MovieListFilterItem(
        isSelected = false,
        type = MovieListFilterItem.FilterType.UPCOMING,
    ),
    MovieListFilterItem(
        isSelected = false,
        type = MovieListFilterItem.FilterType.DISCOVER,
    ),
)
