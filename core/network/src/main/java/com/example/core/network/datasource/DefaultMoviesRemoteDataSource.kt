package com.example.core.network.datasource

import com.example.core.model.Movie
import com.example.core.network.model.response.asDomainObject
import com.example.core.network.service.MovieListApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMoviesRemoteDataSource
    @Inject
    constructor(val moviesApi: MovieListApi) :
    MoviesRemoteDataSource {
        override suspend fun nowPlayingMovies(query: MoviesQuery): List<Movie> =
            moviesApi.nowPlaying(
                language = query.language,
                page = query.page,
                region = query.region,
            ).results.map { it.asDomainObject() }

        override suspend fun popularMovies(query: MoviesQuery): List<Movie> = moviesApi.popular(
            language = query.language,
            page = query.page,
            region = query.region,
        ).results.map { it.asDomainObject() }

        override suspend fun topRatedMovies(query: MoviesQuery): List<Movie> = moviesApi.topRated(
            language = query.language,
            page = query.page,
            region = query.region,
        ).results.map { it.asDomainObject() }

        override suspend fun upcomingMovies(query: MoviesQuery): List<Movie> = moviesApi.upcoming(
            language = query.language,
            page = query.page,
            region = query.region,
        ).results.map { it.asDomainObject() }
    }
