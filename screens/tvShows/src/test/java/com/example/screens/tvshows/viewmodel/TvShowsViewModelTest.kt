package com.example.screens.tvshows.viewmodel

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.example.core.data.repository.TvShowsRepository
import com.example.core.model.TVShow
import com.example.core.model.TVShowsFilterItem
import com.example.core.test.util.MainDispatcherRule
import com.example.core.test.util.TvShowsTestData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvShowsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var tvShowsViewModel: TvShowsViewModel

    private val tvShowsRepository = mockk<TvShowsRepository> {
        coEvery { airingToday(any()) } returns TvShowsTestData.testTVShows()
        coEvery { onTheAir(any()) } returns TvShowsTestData.testTVShows()
        coEvery { popular(any()) } returns TvShowsTestData.testTVShows()
        coEvery { topRated(any()) } returns TvShowsTestData.testTVShows()
    }

    @Before
    fun setUp() {
        tvShowsViewModel = TvShowsViewModel(
            tvShowsRepository = tvShowsRepository,
            dispatcher = mainDispatcherRule.testDispatcher,
        )
    }

    @Test
    fun tvShowsViewModelTest_onLoad_defaultTVShowsFiltersAreSet() = runTest {
        tvShowsViewModel.tvShowsFilters.test {
            val availableFilters = awaitItem()
            assertThat(availableFilters.size).isEqualTo(5)

            with(availableFilters[0]) {
                assertThat(isSelected).isTrue()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.AIRING_TODAY)
            }
            with(availableFilters[1]) {
                assertThat(isSelected).isFalse()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.ON_THE_AIR)
            }
            with(availableFilters[2]) {
                assertThat(isSelected).isFalse()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.POPULAR)
            }
            with(availableFilters[3]) {
                assertThat(isSelected).isFalse()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.TOP_RATED)
            }
            with(availableFilters[4]) {
                assertThat(isSelected).isFalse()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.DISCOVER)
            }
        }
    }

    @Test
    fun tvShowsViewModel_onFilterChange_correctFilterIsSet() = runTest {
        tvShowsViewModel.tvShowsFilters.test {
            tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.AIRING_TODAY)
            with(awaitItem().first { it.type == TVShowsFilterItem.FilterType.AIRING_TODAY }) {
                assertThat(isSelected).isTrue()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.AIRING_TODAY)
            }

            tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.ON_THE_AIR)
            with(awaitItem().first { it.type == TVShowsFilterItem.FilterType.ON_THE_AIR }) {
                assertThat(isSelected).isTrue()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.ON_THE_AIR)
            }

            tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.POPULAR)
            with(awaitItem().first { it.type == TVShowsFilterItem.FilterType.POPULAR }) {
                assertThat(isSelected).isTrue()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.POPULAR)
            }

            tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.TOP_RATED)
            with(awaitItem().first { it.type == TVShowsFilterItem.FilterType.TOP_RATED }) {
                assertThat(isSelected).isTrue()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.TOP_RATED)
            }

            tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.DISCOVER)
            with(awaitItem().first { it.type == TVShowsFilterItem.FilterType.DISCOVER }) {
                assertThat(isSelected).isTrue()
                assertThat(type).isEqualTo(TVShowsFilterItem.FilterType.DISCOVER)
            }
        }
    }

    @Test
    fun tvShowsViewModel_onAiringTodayFilterSelected_showAiringTodayTvShows() = runTest {
        tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.AIRING_TODAY)
        val items: Flow<PagingData<TVShow>> = tvShowsViewModel.tvShows

        val itemSnapshot: List<TVShow> = items.asSnapshot {
            scrollTo(20)
        }

        assertThat(itemSnapshot).containsAtLeastElementsIn(TvShowsTestData.testTVShows()).inOrder()
    }

    @Test
    fun tvShowsViewModel_onTheAirFilterSelected_showOnTheAirTvShows() = runTest {
        tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.ON_THE_AIR)
        val items: Flow<PagingData<TVShow>> = tvShowsViewModel.tvShows

        val itemSnapshot: List<TVShow> = items.asSnapshot {
            scrollTo(20)
        }

        assertThat(itemSnapshot).containsAtLeastElementsIn(TvShowsTestData.testTVShows()).inOrder()
    }

    @Test
    fun tvShowsViewModel_onPopularFilterSelected_showPopularTvShows() = runTest {
        tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.POPULAR)
        val items: Flow<PagingData<TVShow>> = tvShowsViewModel.tvShows

        val itemSnapshot: List<TVShow> = items.asSnapshot {
            scrollTo(20)
        }

        assertThat(itemSnapshot).containsAtLeastElementsIn(TvShowsTestData.testTVShows()).inOrder()
    }

    @Test
    fun tvShowsViewModel_onTopRatedFilterSelected_showTopRatedTvShows() = runTest {
        tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.TOP_RATED)
        val items: Flow<PagingData<TVShow>> = tvShowsViewModel.tvShows

        val itemSnapshot: List<TVShow> = items.asSnapshot {
            scrollTo(20)
        }

        assertThat(itemSnapshot).containsAtLeastElementsIn(TvShowsTestData.testTVShows()).inOrder()
    }

    @Test
    fun tvShowsViewModel_onDiscoveredFilterSelected_showDiscoveredTvShows() = runTest {
        tvShowsViewModel.onFilterChange(TVShowsFilterItem.FilterType.DISCOVER)
        val items: Flow<PagingData<TVShow>> = tvShowsViewModel.tvShows

        val itemSnapshot: List<TVShow> = items.asSnapshot {
            scrollTo(20)
        }

        assertThat(itemSnapshot).containsAtLeastElementsIn(TvShowsTestData.testTVShows()).inOrder()
    }
}
