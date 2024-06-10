package com.example.core.data.repository

import com.example.core.common.injection.IoDispatcher
import com.example.core.common.outcome.Failure
import com.example.core.common.outcome.Outcome
import com.example.core.common.outcome.Success
import com.example.core.model.Movie
import com.example.core.model.MovieCredit
import com.example.core.model.MovieDetails
import com.example.core.model.Video
import com.example.core.network.datasource.MovieDetailsRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface MovieDetailsRepository {
    fun movieDetails(movieId: Long): Flow<Outcome<MovieDetails>>

    fun movieCredits(movieId: Long): Flow<Outcome<MovieCredit>>

    fun movieVideos(movieId: Long): Flow<Outcome<List<Video>>>

    fun similarMovies(movieId: Long): Flow<Outcome<List<Movie>>>
}

@Singleton
class DefaultMovieDetailsRepository @Inject constructor(
    private val remoteDataSource: MovieDetailsRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): MovieDetailsRepository {
    override fun movieDetails(movieId: Long): Flow<Outcome<MovieDetails>> = flow {
        try {
            val response = remoteDataSource.movieDetails(movieId)
            emit(Success(response))
        } catch (e: Throwable) {
            emit(Failure(errorResponse = "Error loading movie details", throwable = e))
        }
    }.flowOn(dispatcher)

    override fun movieCredits(movieId: Long): Flow<Outcome<MovieCredit>> = flow {
        try {
            val response = remoteDataSource.movieCredits(movieId)
            emit(Success(response))
        } catch (e: Throwable) {
            e.printStackTrace()
            emit(Failure(errorResponse = "Error getting movie credits"))
        }
    }.flowOn(dispatcher)

    override fun movieVideos(movieId: Long): Flow<Outcome<List<Video>>> = flow {
        try {
            val response = remoteDataSource.movieVideos(movieId)
            emit(Success(response))
        } catch (e: Throwable) {
            emit(Failure(errorResponse = "Error getting movie videos"))
        }
    }.flowOn(dispatcher)

    override fun similarMovies(movieId: Long): Flow<Outcome<List<Movie>>> = flow {
        try {
            val response = remoteDataSource.similarMovies(movieId)
            emit(Success(response))
        } catch (e: Throwable) {
            e.printStackTrace()
            emit(Failure(errorResponse = "Error getting recommended movies"))
        }
    }.flowOn(dispatcher)

}