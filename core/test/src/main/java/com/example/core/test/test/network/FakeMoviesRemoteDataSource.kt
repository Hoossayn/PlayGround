package com.example.core.test.test.network

import com.example.core.model.Movie
import com.example.core.network.datasource.MoviesQuery
import com.example.core.network.datasource.MoviesRemoteDataSource
import com.example.core.test.util.testMovies
import javax.inject.Inject

class FakeMoviesRemoteDataSource @Inject constructor() : MoviesRemoteDataSource {
    override suspend fun nowPlayingMovies(query: MoviesQuery): List<Movie> = testMovies()

    override suspend fun popularMovies(query: MoviesQuery): List<Movie> = testMovies()

    override suspend fun topRatedMovies(query: MoviesQuery): List<Movie> = testMovies()

    override suspend fun upcomingMovies(query: MoviesQuery): List<Movie> = testMovies()
}
