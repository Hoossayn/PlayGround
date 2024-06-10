package com.example.screens.moviedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.outcome.Failure
import com.example.core.common.outcome.Success
import com.example.core.data.repository.MovieDetailsRepository
import com.example.core.model.Movie
import com.example.core.model.MovieCredit
import com.example.core.model.MovieDetails
import com.example.core.model.Video
import com.example.screens.moviedetails.navigation.MovieDetailsArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetailsRepository: MovieDetailsRepository
): ViewModel() {
    private val movieId = MovieDetailsArg(savedStateHandle).movieId

    @Suppress("MagicNumber")
    val movieDetailsUiState: StateFlow<MovieDetailsUiState> = movieId.flatMapLatest {
        movieDetailsRepository.movieDetails(it)
    }.map {
        when (it) {
            is Failure -> MovieDetailsUiState.LoadFailed(it.errorResponse)
            is Success -> MovieDetailsUiState.Success(it.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieDetailsUiState.Loading
    )

    @Suppress("MagicNumber")
    val movieCreditUiState: StateFlow<MovieCreditUiState> = movieId.flatMapLatest {
        movieDetailsRepository.movieCredits(it)
    }.map {
        when (it) {
            is Failure -> MovieCreditUiState.LoadFailed(it.errorResponse)
            is Success -> MovieCreditUiState.Success(it.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MovieCreditUiState.Loading
    )

    @Suppress("MagicNumber")
    val similarMoviesUiState: StateFlow<SimilarMoviesUiState> = movieId.flatMapLatest {
        movieDetailsRepository.similarMovies(it)
    }.map {
        when (it) {
            is Failure -> SimilarMoviesUiState.LoadFailed(it.errorResponse)
            is Success -> SimilarMoviesUiState.Success(it.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SimilarMoviesUiState.Loading
    )

    @Suppress("MagicNumber")
    val moviesVideoUiState: StateFlow<MoviesVideoUiState> = movieId.flatMapLatest {
        movieDetailsRepository.movieVideos(it)
    }.map {
        when (it) {
            is Failure -> MoviesVideoUiState.LoadFailed(it.errorResponse)
            is Success -> MoviesVideoUiState.Success(it.data, movieId.value)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MoviesVideoUiState.Loading,
    )
}

sealed interface MoviesVideoUiState {
    data object Loading : MoviesVideoUiState

    data class Success(val videos: List<Video>, val movieId: Long) : MoviesVideoUiState

    data class LoadFailed(val message: String) : MoviesVideoUiState
}

sealed interface SimilarMoviesUiState {
    data object Loading : SimilarMoviesUiState

    data class Success(val movies: List<Movie>) : SimilarMoviesUiState

    data class LoadFailed(val message: String) : SimilarMoviesUiState
}

sealed interface MovieCreditUiState {
    data object Loading : MovieCreditUiState

    data class Success(val movieCredit: MovieCredit) : MovieCreditUiState

    data class LoadFailed(val message: String) : MovieCreditUiState
}

sealed interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState

    data class Success(val movieDetails: MovieDetails) : MovieDetailsUiState

    data class LoadFailed(val message: String) : MovieDetailsUiState
}