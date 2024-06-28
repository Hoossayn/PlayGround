package com.example.screens.tvshows.ui

import android.os.Build
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core.model.TVShow
import com.example.core.model.TVShowsFilterItem
import com.example.core.test.util.TvShowsTestData
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
class TvShowsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var onTVShowClick = 0
    private var onFilterItemSelected = 0

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out
    }

    @Test
    fun tvShowsScreen_tvShowsLoading_showLoadingProgress() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = emptyList<TVShow>(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.Loading,
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false)
                            )
                        )
                    ).collectAsLazyPagingItems(),
                    filters = testFilters(),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ }
                )
            }
            onNodeWithTag(FRESH_LOAD_PROGRESS_TEST_TAG).assert(
                hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate),
            ).assertIsDisplayed()
        }
    }

    @Test
    fun tvShowsScreen_appendTvShowsLoading_showLoadingProgress() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = emptyList<TVShow>(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.Loading,
                                prepend = LoadState.NotLoading(false),
                            ),
                        ),
                    ).collectAsLazyPagingItems(),
                    filters = testFilters(),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ },
                )
            }

            onNodeWithTag(APPEND_LOAD_PROGRESS_TEST_TAG).assert(
                hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate),
            ).assertIsDisplayed()
        }
    }

    @Test
    fun tvShowsScreen_tvShowsLoaded_tvShowsAreVisible() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = TvShowsTestData.testTVShows(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                            ),
                        ),
                    ).collectAsLazyPagingItems(),
                    filters = testFilters(),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ },
                )
            }

            composeTestRule.onNodeWithTag(TV_SHOWS_GRID_ITEMS_TEST_TAG)
                .assertExists()
                .assert(hasScrollAction())
                .onChildren()
                .onFirst()
                .performClick()

            assertThat(onTVShowClick).isEqualTo(1)
        }
    }

    @Test
    fun tvShowsScreen_airingTodayFilterSelected_correctFilterIsSelected() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = emptyList<TVShow>(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                            ),
                        ),
                    ).collectAsLazyPagingItems(),
                    filters = testFilters().selectItem(TVShowsFilterItem.FilterType.AIRING_TODAY),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ },
                )
            }

            composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
                .assertExists()
                .assert(hasScrollAction())
                .assertIsDisplayed()

            composeTestRule.onNode(hasText("Airing Today") and hasClickAction())
                .assertExists()
                .assertIsSelected()
                .assertIsDisplayed()
                .performClick()

            assertThat(onFilterItemSelected).isEqualTo(1)
        }
    }

    @Test
    fun tvShowsScreen_onTheAirFilterSelected_correctFilterIsSelected() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = emptyList<TVShow>(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                            ),
                        ),
                    ).collectAsLazyPagingItems(),
                    filters = testFilters().selectItem(TVShowsFilterItem.FilterType.ON_THE_AIR),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ },
                )
            }

            composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
                .assertExists()
                .assert(hasScrollAction())
                .assertIsDisplayed()

            composeTestRule.onNode(hasText("On The Air") and hasClickAction())
                .assertExists()
                .assertIsSelected()
                .assertIsDisplayed()
                .performClick()

            assertThat(onFilterItemSelected).isEqualTo(1)
        }
    }

    @Test
    fun tvShowsScreen_popularFilterSelected_correctFilterIsSelected() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = emptyList<TVShow>(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                            ),
                        ),
                    ).collectAsLazyPagingItems(),
                    filters = testFilters().selectItem(TVShowsFilterItem.FilterType.POPULAR),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ },
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

            assertThat(onFilterItemSelected).isEqualTo(1)
        }
    }

    @Test
    fun tvShowsScreen_topRatedFilterSelected_correctFilterIsSelected() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = emptyList<TVShow>(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                            ),
                        ),
                    ).collectAsLazyPagingItems(),
                    filters = testFilters().selectItem(TVShowsFilterItem.FilterType.TOP_RATED),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ },
                )
            }

            composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
                .assertExists()
                .assert(hasScrollAction())
                .assertIsDisplayed()

            composeTestRule.onNode(hasText("Top Rated") and hasClickAction())
                .assertExists()
                .assertIsSelected()
                .assertIsDisplayed()
                .performClick()

            assertThat(onFilterItemSelected).isEqualTo(1)
        }
    }

    @Test
    fun tvShowsScreen_discoverFilterSelected_correctFilterIsSelected() {
        composeTestRule.apply {
            setContent {
                TVShowsScreen(
                    tvShowLazyPagingItems = flowOf(
                        PagingData.from(
                            data = emptyList<TVShow>(),
                            sourceLoadStates = LoadStates(
                                refresh = LoadState.NotLoading(false),
                                append = LoadState.NotLoading(false),
                                prepend = LoadState.NotLoading(false),
                            ),
                        ),
                    ).collectAsLazyPagingItems(),
                    filters = testFilters().selectItem(TVShowsFilterItem.FilterType.DISCOVER),
                    onTVShowClick = { onTVShowClick++ },
                    onFilterItemSelected = { onFilterItemSelected++ },
                )
            }

            composeTestRule.onNodeWithTag(CHIP_GROUP_TEST_TAG)
                .assertExists()
                .assert(hasScrollAction())
                .assertIsDisplayed()

            composeTestRule.onNode(hasText("Discover") and hasClickAction())
                .assertExists()
                .assertIsSelected()
                .assertIsDisplayed()
                .performClick()

            onNodeWithTag(SEARCH_TEST_TAG).assertIsDisplayed()
            onNodeWithText("Search ....").assertIsDisplayed()
            onNodeWithContentDescription("perform search").assertIsDisplayed()
            onNodeWithContentDescription("close search").assertIsDisplayed()
            onNodeWithText("Adult").assertIsDisplayed()
            onNodeWithText("Video").assertIsDisplayed()
            onNodeWithTag(SEARCH_ADULT).assertIsDisplayed()
            onNodeWithTag(SEARCH_INLCUDE_VIDEO).assertIsDisplayed()
            assertThat(onFilterItemSelected).isEqualTo(0)
        }
    }

    private fun List<TVShowsFilterItem>.selectItem(whereType: TVShowsFilterItem.FilterType) = map {
        if (it.type == whereType) {
            it.copy(isSelected = true)
        } else {
            it.copy(isSelected = false)
        }
    }

    private fun testFilters() = listOf(
        TVShowsFilterItem(
            isSelected = true,
            type = TVShowsFilterItem.FilterType.AIRING_TODAY,
        ),
        TVShowsFilterItem(
            isSelected = false,
            type = TVShowsFilterItem.FilterType.ON_THE_AIR,
        ),
        TVShowsFilterItem(
            isSelected = false,
            type = TVShowsFilterItem.FilterType.POPULAR,
        ),
        TVShowsFilterItem(
            isSelected = false,
            type = TVShowsFilterItem.FilterType.TOP_RATED,
        ),
        TVShowsFilterItem(
            isSelected = false,
            type = TVShowsFilterItem.FilterType.DISCOVER,
        ),
    )
}
