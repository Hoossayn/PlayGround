package com.example.core.test.test.data

import com.example.core.data.filter.MoviesFilter
import com.example.core.data.repository.MovieListRepository
import com.example.core.model.Movie
import com.example.core.test.util.testMovies
import javax.inject.Inject

class FakeMoviesRepository
    @Inject
    constructor() : MovieListRepository{
        override suspend fun nowPlayingMovies(filter: MoviesFilter): List<Movie> = testMovies()

        override suspend fun popularMovies(filter: MoviesFilter): List<Movie> = testMovies()

        override suspend fun topRatedMovies(filter: MoviesFilter): List<Movie> = testMovies()

        override suspend fun upComingMovies(filter: MoviesFilter): List<Movie> = testMovies()
}
