package com.example.core.network.datasource

import com.example.core.model.Movie
import com.example.core.model.MovieCredit
import com.example.core.model.MovieDetails
import com.example.core.model.Video

interface MovieDetailsRemoteDataSource {
    suspend fun movieDetails(movieId: Long): MovieDetails

    suspend fun movieCredits(movieId: Long): MovieCredit

    suspend fun movieVideos(movieId: Long): List<Video>

    suspend fun similarMovies(movieId: Long): List<Movie>
}
