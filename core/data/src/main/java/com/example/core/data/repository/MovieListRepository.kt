package com.example.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.common.injection.IoDispatcher
import com.example.core.data.filter.MoviesFilter
import com.example.core.data.filter.toQuery
import com.example.core.model.Movie
import com.example.core.network.datasource.MoviesRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface MovieListRepository {
    suspend fun nowPlayingMovies(filter: MoviesFilter): List<Movie>

    suspend fun popularMovies(filter: MoviesFilter): List<Movie>

    suspend fun topRatedMovies(filter: MoviesFilter): List<Movie>

    suspend fun upComingMovies(filter: MoviesFilter): List<Movie>
}

class DefaultMovieListRepository
    @Inject
    constructor(
        private val moviesRemoteDataSource: MoviesRemoteDataSource,
        @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : MovieListRepository {
    override suspend fun nowPlayingMovies(filter: MoviesFilter): List<Movie> =
        withContext(defaultDispatcher) {
            moviesRemoteDataSource.nowPlayingMovies(filter.toQuery())
        }

    override suspend fun popularMovies(filter: MoviesFilter): List<Movie> =
        withContext(defaultDispatcher) {
            moviesRemoteDataSource.popularMovies(filter.toQuery())
        }

    override suspend fun topRatedMovies(filter: MoviesFilter): List<Movie> =
        withContext(defaultDispatcher) {
            moviesRemoteDataSource.topRatedMovies(filter.toQuery())
        }

    override suspend fun upComingMovies(filter: MoviesFilter): List<Movie> =
        withContext(defaultDispatcher) {
            moviesRemoteDataSource.upcomingMovies(filter.toQuery())
        }
}

class MoviesListPagingSource(private val fetchTvShows: suspend (Int) -> List<Movie>):
    PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: 1
            val response = fetchTvShows(pageNumber)
            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (response.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(data = response, prevKey = prevKey, nextKey = nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}
