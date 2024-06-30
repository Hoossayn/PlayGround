package com.example.core.network.datasource

import com.example.core.model.Movie
import com.example.core.model.MovieCredit
import com.example.core.model.MovieDetails
import com.example.core.model.Video
import com.example.core.network.model.response.asDomainObject
import com.example.core.network.service.MovieDetailsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMovieDetailsRemoteDataSource
    @Inject
    constructor(private val movieDetailsApi: MovieDetailsApi) : MovieDetailsRemoteDataSource {
        override suspend fun movieDetails(movieId: Long): MovieDetails =
            movieDetailsApi.movieDetails(movieId).asDomainObject()

        override suspend fun movieCredits(movieId: Long): MovieCredit =
            movieDetailsApi.movieCredits(movieId).asDomainObject()

        override suspend fun movieVideos(movieId: Long): List<Video> =
            movieDetailsApi.movieVideos(movieId).results.map { it.asDomainObject() }

        override suspend fun similarMovies(movieId: Long): List<Movie> =
            movieDetailsApi.similar(movieId).results.map { it.asDomainObject() }
    }
