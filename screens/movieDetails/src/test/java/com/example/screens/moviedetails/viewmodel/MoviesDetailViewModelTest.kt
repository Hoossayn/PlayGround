package com.example.screens.moviedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.core.common.outcome.Failure
import com.example.core.common.outcome.Outcome
import com.example.core.common.outcome.Success
import com.example.core.data.repository.MovieDetailsRepository
import com.example.core.model.Movie
import com.example.core.model.MovieCredit
import com.example.core.model.MovieDetails
import com.example.core.model.Video
import com.example.core.test.util.MainDispatcherRule
import com.example.core.test.util.MovieDetailsTestData
import com.example.core.test.util.testMovies
import com.example.screens.moviedetails.navigation.movieIdArg
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.shadows.ShadowLog
import kotlin.test.assertEquals

const val TEST_ID = 0L

@RunWith(JUnit4::class)
class MoviesDetailViewModelTest {
    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private val savedStateHandle = SavedStateHandle().apply {
        this[movieIdArg] = TEST_ID
    }
    private val movieDetailsRepository = mockk<MovieDetailsRepository>()

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
        movieDetailsViewModel = MovieDetailsViewModel(
            savedStateHandle = savedStateHandle,
            movieDetailsRepository = movieDetailsRepository
        )
    }

    @Test
    fun movieDetailsViewModel_loadMovieDetailsSuccessful_sendMoviesDetails() = runTest {
        every { movieDetailsRepository.movieDetails(any()) } returns flowOf<Outcome<MovieDetails>>(
            Success(MovieDetailsTestData.testMovieDetails(TEST_ID))
        )

        movieDetailsViewModel.movieDetailsUiState.test {
            assertThat(awaitItem()).isEqualTo(MovieDetailsUiState.Loading)
            with((awaitItem() as MovieDetailsUiState.Success)) {
                assertThat(movieDetails).isEqualTo(MovieDetailsTestData.testMovieDetails(TEST_ID))
            }
        }

        assertEquals(savedStateHandle[movieIdArg], TEST_ID)
        verify(exactly = 1) {movieDetailsRepository.movieDetails(TEST_ID)}
    }

    @Test
    fun movieDetailsViewModel_loadMovieDetailsFailed_sendError() = runTest {
        every { movieDetailsRepository.movieDetails(any()) } returns flowOf<Outcome<MovieDetails>>(
            Failure("Error loading movies"),
        )

        movieDetailsViewModel.movieDetailsUiState.test {
            assertThat(awaitItem()).isEqualTo(MovieDetailsUiState.Loading)
            with((awaitItem() as MovieDetailsUiState.LoadFailed)) {
                assertThat(message).isEqualTo("Error loading movies")
            }
        }

        assertEquals(savedStateHandle[movieIdArg], TEST_ID)
        verify(exactly = 1) { movieDetailsRepository.movieDetails(TEST_ID) }
    }

    @Test
    fun movieDetailsViewModel_loadMovieCreditSuccess_sendMovieCredit() = runTest {
        every { movieDetailsRepository.movieCredits(any()) } returns flowOf<Outcome<MovieCredit>>(
            Success(MovieDetailsTestData.testMovieCredit(TEST_ID)),
        )

        movieDetailsViewModel.movieCreditUiState.test {
            assertThat(awaitItem()).isEqualTo(MovieCreditUiState.Loading)
            with((awaitItem() as MovieCreditUiState.Success)) {
                assertThat(movieCredit).isEqualTo(MovieDetailsTestData.testMovieCredit(TEST_ID))
            }
        }

        assertEquals(savedStateHandle[movieIdArg], TEST_ID)
        verify(exactly = 1) { movieDetailsRepository.movieCredits(TEST_ID) }
    }

    @Test
    fun movieDetailsViewModel_loadMovieCreditFailed_sendError() = runTest {
        every { movieDetailsRepository.movieCredits(any()) } returns flowOf<Outcome<MovieCredit>>(
            Failure("Error loading movie credits"),
        )

        movieDetailsViewModel.movieCreditUiState.test {
            assertThat(awaitItem()).isEqualTo(MovieCreditUiState.Loading)
            with((awaitItem() as MovieCreditUiState.LoadFailed)) {
                assertThat(message).isEqualTo("Error loading movie credits")
            }
        }

        assertEquals(savedStateHandle[movieIdArg], TEST_ID)
        verify(exactly = 1) { movieDetailsRepository.movieCredits(TEST_ID) }
    }

    @Test
    fun movieDetailsViewModel_loadSimilarMovieSuccess_sendSimilarMovies() = runTest {
        every { movieDetailsRepository.similarMovies(any()) } returns flowOf<Outcome<List<Movie>>>(
            Success(testMovies()),
        )

        movieDetailsViewModel.similarMoviesUiState.test {
            assertThat(awaitItem()).isEqualTo(SimilarMoviesUiState.Loading)
            with((awaitItem() as SimilarMoviesUiState.Success)) {
                assertThat(movies).containsExactlyElementsIn(testMovies())
                    .inOrder()
            }
        }

        assertEquals(savedStateHandle[movieIdArg], TEST_ID)
        verify(exactly = 1) { movieDetailsRepository.similarMovies(TEST_ID) }
    }

    @Test
    fun movieDetailsViewModel_loadSimilarMovieFailed_sendError() = runTest {
        every { movieDetailsRepository.similarMovies(any()) } returns flowOf<Outcome<List<Movie>>>(
            Failure("Error loading similar movies"),
        )

        movieDetailsViewModel.similarMoviesUiState.test {
            assertThat(awaitItem()).isEqualTo(SimilarMoviesUiState.Loading)
            with((awaitItem() as SimilarMoviesUiState.LoadFailed)) {
                assertThat(message).isEqualTo("Error loading similar movies")
            }
        }

        assertEquals(savedStateHandle[movieIdArg], TEST_ID)
        verify(exactly = 1) { movieDetailsRepository.similarMovies(TEST_ID) }
    }

    @Test
    fun movieDetailsViewModel_loadMovieVideoSuccess_sendMoviesVideos() = runTest {
        every { movieDetailsRepository.movieVideos(any()) } returns flowOf<Outcome<List<Video>>>(
            Success(MovieDetailsTestData.testMovieVideos(TEST_ID)),
        )

        movieDetailsViewModel.moviesVideoUiState.test {
            assertThat(awaitItem()).isEqualTo(MoviesVideoUiState.Loading)
            with((awaitItem() as MoviesVideoUiState.Success)) {
                assertThat(videos)
                    .containsExactlyElementsIn(MovieDetailsTestData.testMovieVideos(TEST_ID))
                    .inOrder()
            }
        }

        assertEquals(savedStateHandle[movieIdArg], TEST_ID)
        verify(exactly = 1) { movieDetailsRepository.movieVideos(TEST_ID) }
    }

    @Test
    fun movieDetailsViewModel_loadMovieVideoFailed_sendError() = runTest {
        every { movieDetailsRepository.movieVideos(any()) } returns flowOf<Outcome<List<Video>>>(
            Failure("Error loading movie videos"),
        )

        movieDetailsViewModel.moviesVideoUiState.test {
            assertThat(awaitItem()).isEqualTo(MoviesVideoUiState.Loading)
            with((awaitItem() as MoviesVideoUiState.LoadFailed)) {
                assertThat(message).isEqualTo("Error loading movie videos")
            }

            assertEquals(savedStateHandle[movieIdArg], TEST_ID)
            verify(exactly = 1) { movieDetailsRepository.movieVideos(TEST_ID) }
        }
    }
}























