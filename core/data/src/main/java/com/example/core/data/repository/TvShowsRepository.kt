package com.example.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.common.injection.IoDispatcher
import com.example.core.data.filter.MoviesFilter
import com.example.core.model.TVShow
import com.example.core.network.model.response.asDomainObject
import com.example.core.network.service.TVShowsListApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface TvShowsRepository {
    suspend fun airingToday(filter: MoviesFilter): List<TVShow>

    suspend fun onTheAir(filter: MoviesFilter): List<TVShow>

    suspend fun popular(filter: MoviesFilter): List<TVShow>

    suspend fun topRated(filter: MoviesFilter): List<TVShow>
}

class DefaultTvShowsRepository
    @Inject
    constructor(
        private val tvSeriesListApi: TVShowsListApi,
        @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : TvShowsRepository {
    override suspend fun airingToday(filter: MoviesFilter): List<TVShow> =
        withContext(defaultDispatcher) {
            tvSeriesListApi.airingToday(
                filter.language,
                filter.page,
                filter.region,
            ).results.map { it.asDomainObject() }
        }

    override suspend fun onTheAir(filter: MoviesFilter): List<TVShow> =
        withContext(defaultDispatcher) {
            tvSeriesListApi.onTheAir(
                filter.language,
                filter.page,
                filter.region,
            ).results.map { it.asDomainObject() }
        }

    override suspend fun popular(filter: MoviesFilter): List<TVShow> =
        withContext(defaultDispatcher) {
            tvSeriesListApi.popular(
                filter.language,
                filter.page,
                filter.region,
            ).results.map { it.asDomainObject() }
        }

    override suspend fun topRated(filter: MoviesFilter): List<TVShow> =
        withContext(defaultDispatcher) {
            tvSeriesListApi.topRated(
                filter.language,
                filter.page,
                filter.region,
            ).results.map { it.asDomainObject() }
        }
}

class TvShowsListPagingSource(private val fetchMovies: suspend (Int) -> List<TVShow>) :
    PagingSource<Int, TVShow>() {
    override fun getRefreshKey(state: PagingState<Int, TVShow>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShow> {
        return try {
            val pageNumber = params.key ?: 1
            val response = fetchMovies(pageNumber)
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
