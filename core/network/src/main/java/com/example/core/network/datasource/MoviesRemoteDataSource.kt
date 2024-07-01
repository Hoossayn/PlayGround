package com.example.core.network.datasource

import com.example.core.model.Movie

interface MoviesRemoteDataSource {
    suspend fun nowPlayingMovies(query: MoviesQuery): List<Movie>

    suspend fun popularMovies(query: MoviesQuery): List<Movie>

    suspend fun topRatedMovies(query: MoviesQuery): List<Movie>

    suspend fun upcomingMovies(query: MoviesQuery): List<Movie>
}
