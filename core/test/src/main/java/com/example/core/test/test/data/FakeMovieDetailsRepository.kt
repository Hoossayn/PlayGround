package com.example.core.test.test.data

import com.example.core.common.outcome.Outcome
import com.example.core.common.outcome.Success
import com.example.core.data.repository.MovieDetailsRepository
import com.example.core.model.Movie
import com.example.core.model.MovieCredit
import com.example.core.model.MovieDetails
import com.example.core.model.Video
import com.example.core.test.util.MovieDetailsTestData
import com.example.core.test.util.testMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeMovieDetailsRepository
    @Inject
    constructor() : MovieDetailsRepository {
        override fun movieDetails(movieId: Long): Flow<Outcome<MovieDetails>> =
            flowOf(Success(MovieDetailsTestData.testMovieDetails(0L)))

        override fun movieCredits(movieId: Long): Flow<Outcome<MovieCredit>> = flowOf(
            Success(MovieDetailsTestData.testMovieCredit(0L)),
        )

        override fun movieVideos(movieId: Long): Flow<Outcome<List<Video>>> = flowOf(
            Success(MovieDetailsTestData.testMovieVideos(0L)),
        )

        override fun similarMovies(movieId: Long): Flow<Outcome<List<Movie>>> = flowOf(
            Success(testMovies()),
        )
    }
