package com.example.movies.viewmodel

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.example.core.data.repository.MovieListRepository
import com.example.core.model.Movie
import com.example.core.model.MovieListFilterItem.FilterType
import com.example.core.test.util.MainDispatcherRule
import com.example.core.test.util.testMovies
import com.example.screens.movies.viewmodel.MoviesViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var moviesViewModel: MoviesViewModel

    private val movieListRepository = mockk<MovieListRepository> {
        coEvery { nowPlayingMovies(any()) } returns testMovies()
        coEvery { popularMovies(any()) } returns testMovies()
        coEvery { topRatedMovies(any()) } returns testMovies()
        coEvery { upComingMovies(any()) } returns testMovies()
    }

    @Before
    fun setUp(){
        moviesViewModel = MoviesViewModel(
            movieListRepository = movieListRepository,
            dispatcher = mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun moviesViewModel_init_defaultAvailableFilterSet() = runTest {
        moviesViewModel.availableFilters.test {
            val availableFilters = awaitItem()
            assertThat(availableFilters).isNotEmpty()
            assertThat(availableFilters.size).isEqualTo(5)

            with(availableFilters[0]) {
                assertThat(isSelected).isTrue()
                assertThat(type).isEqualTo(FilterType.NOW_PLAYING)
            }

            with(availableFilters[1]) {
                assertThat(isSelected).isFalse()
                assertThat(type).isEqualTo(FilterType.POPULAR)
            }

            with(availableFilters[2]) {
                assertThat(isSelected).isFalse()
                assertThat(type).isEqualTo(FilterType.TOP_RATED)
            }

            with(availableFilters[3]) {
                assertThat(isSelected).isFalse()
                assertThat(type).isEqualTo(FilterType.UPCOMING)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun moviesViewModel_OnEvent_CorrectFilterIsSet() {
        val availableFilters = moviesViewModel.availableFilters.value
        assertThat(availableFilters).isNotEmpty()
        assertThat(availableFilters.size).isEqualTo(5)

        with(availableFilters[0]) {
            assertThat(isSelected).isTrue()
            assertThat(type).isEqualTo(FilterType.NOW_PLAYING)
        }

        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.POPULAR))
        assertThat(
            moviesViewModel.availableFilters.value.first {
                it.type == FilterType.POPULAR
            }.isSelected,
        ).isTrue()

        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.TOP_RATED))
        assertThat(
            moviesViewModel.availableFilters.value.first {
                it.type == FilterType.TOP_RATED
            }.isSelected,
        ).isTrue()

        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.UPCOMING))
        assertThat(
            moviesViewModel.availableFilters.value.first {
                it.type == FilterType.UPCOMING
            }.isSelected,
        ).isTrue()

        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.NOW_PLAYING))
        assertThat(
            moviesViewModel.availableFilters.value.first {
                it.type == FilterType.NOW_PLAYING
            }.isSelected,
        ).isTrue()
    }

    @Test
    fun moviesViewModel_nowPlayingFilterSelected_showNowPlayingMovies() = runTest {
        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.NOW_PLAYING))
        val items: Flow<PagingData<Movie>> = moviesViewModel.movies
        val itemsSnapshot: List<Movie> = items.asSnapshot {
            scrollTo(index = 20)
        }

        assertThat(itemsSnapshot).containsAtLeastElementsIn(testMovies()).inOrder()
        coVerify { movieListRepository.nowPlayingMovies(any()) }
    }

    @Test
    fun moviesViewModel_popularFilterSelected_showPopularMovies() = runTest {
        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.POPULAR))
        val items: Flow<PagingData<Movie>> = moviesViewModel.movies
        val itemsSnapshot: List<Movie> = items.asSnapshot {
            scrollTo(index = 20)
        }

        assertThat(itemsSnapshot).containsAtLeastElementsIn(testMovies()).inOrder()
        coVerify { movieListRepository.popularMovies(any()) }
    }

    @Test
    fun moviesViewModel_topRatedFilterSelected_showTopRatedMovies() = runTest {
        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.TOP_RATED))
        val items: Flow<PagingData<Movie>> = moviesViewModel.movies
        val itemsSnapshot: List<Movie> = items.asSnapshot {
            scrollTo(index = 20)
        }

        assertThat(itemsSnapshot).containsAtLeastElementsIn(testMovies()).inOrder()
        coVerify { movieListRepository.topRatedMovies(any()) }
    }

    @Test
    fun moviesViewModel_upComingFilterSelected_showUpComingMovies() = runTest {
        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.UPCOMING))
        val items: Flow<PagingData<Movie>> = moviesViewModel.movies
        val itemsSnapshot: List<Movie> = items.asSnapshot {
            scrollTo(index = 20)
        }

        assertThat(itemsSnapshot).containsAtLeastElementsIn(testMovies()).inOrder()
        coVerify { movieListRepository.upComingMovies(any()) }
    }

    @Test
    fun moviesViewModel_discoveredFilterSelected_showUpDiscoverMovies() = runTest {
        moviesViewModel.onEvent(MoviesViewModel.Event.OnFilterSelected(FilterType.DISCOVER))
        val items: Flow<PagingData<Movie>> = moviesViewModel.movies
        val itemsSnapshot: List<Movie> = items.asSnapshot {
            scrollTo(index = 20)
        }

        assertThat(itemsSnapshot).containsAtLeastElementsIn(testMovies()).inOrder()
        coVerify { movieListRepository.upComingMovies(any()) }
    }
}