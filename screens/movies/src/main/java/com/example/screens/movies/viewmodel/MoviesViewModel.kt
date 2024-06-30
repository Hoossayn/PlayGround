package com.example.screens.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.core.common.injection.IoDispatcher
import com.example.core.data.filter.MoviesFilter
import com.example.core.data.repository.MovieListRepository
import com.example.core.data.repository.MoviesListPagingSource
import com.example.core.model.MovieListFilterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _availableFilters = MutableStateFlow(
        listOf(
            MovieListFilterItem(
                isSelected = true,
                type = MovieListFilterItem.FilterType.NOW_PLAYING,
            ),
            MovieListFilterItem(
                isSelected = false,
                type = MovieListFilterItem.FilterType.POPULAR,
            ),
            MovieListFilterItem(
                isSelected = false,
                type = MovieListFilterItem.FilterType.TOP_RATED,
            ),
            MovieListFilterItem(
                isSelected = false,
                type = MovieListFilterItem.FilterType.UPCOMING,
            ),
            MovieListFilterItem(
                isSelected = false,
                type = MovieListFilterItem.FilterType.DISCOVER,
            ),
        ),
    )

    val availableFilters: StateFlow<List<MovieListFilterItem>> = _availableFilters.asStateFlow()

    private val currentFilter = MutableStateFlow(MovieListFilterItem.FilterType.NOW_PLAYING)

    val movies = currentFilter.flatMapLatest { filter ->
        Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200, enablePlaceholders = true),
            initialKey = null,
            pagingSourceFactory = {
                MoviesListPagingSource { page ->
                    val queryFilter = MoviesFilter(
                        language = "en-Us",
                        page = page,
                        region = null,
                    )

                    when (filter) {
                        MovieListFilterItem.FilterType.NOW_PLAYING ->
                            movieListRepository.nowPlayingMovies(queryFilter)

                        MovieListFilterItem.FilterType.POPULAR ->
                            movieListRepository.popularMovies(queryFilter)

                        MovieListFilterItem.FilterType.TOP_RATED ->
                            movieListRepository.topRatedMovies(queryFilter)

                        MovieListFilterItem.FilterType.UPCOMING ->
                            movieListRepository.upComingMovies(queryFilter)

                        MovieListFilterItem.FilterType.DISCOVER ->
                            movieListRepository.upComingMovies(queryFilter)
                    }
                }
             },
            ).flow.cachedIn(viewModelScope)
    }.flowOn(dispatcher)

    sealed interface Event {
        data class OnFilterSelected(val filter: MovieListFilterItem.FilterType): Event
    }

    fun onEvent(event:Event) {
        when (event){
            is Event.OnFilterSelected -> {
                val updatedFilters = _availableFilters.value.map { filterItem ->
                    if (filterItem.type == event.filter) {
                        filterItem.copy(isSelected = true)
                    } else {
                        filterItem.copy(isSelected = false)
                    }
                }
                currentFilter.update { event.filter }
                _availableFilters.update { updatedFilters }
            }
        }
    }

}


