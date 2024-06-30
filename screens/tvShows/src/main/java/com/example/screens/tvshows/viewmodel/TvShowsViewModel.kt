package com.example.screens.tvshows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.core.common.injection.IoDispatcher
import com.example.core.data.filter.MoviesFilter
import com.example.core.data.repository.TvShowsListPagingSource
import com.example.core.data.repository.TvShowsRepository
import com.example.core.model.TVShowsFilterItem
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
class TvShowsViewModel
    @Inject
    constructor(
        private val tvShowsRepository: TvShowsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    @Suppress("ktlint:standard:property-naming")
    private val _tvShowFilters = MutableStateFlow(
        listOf(
            TVShowsFilterItem(
                isSelected = true,
                type = TVShowsFilterItem.FilterType.AIRING_TODAY,
            ),
            TVShowsFilterItem(
                isSelected = false,
                type = TVShowsFilterItem.FilterType.ON_THE_AIR,
            ),
            TVShowsFilterItem(
                isSelected = false,
                type = TVShowsFilterItem.FilterType.POPULAR,
            ),
            TVShowsFilterItem(
                isSelected = false,
                type = TVShowsFilterItem.FilterType.TOP_RATED,
            ),
            TVShowsFilterItem(
                isSelected = false,
                type = TVShowsFilterItem.FilterType.DISCOVER,
            ),
        ),
    )

    val tvShowsFilters: StateFlow<List<TVShowsFilterItem>> = _tvShowFilters.asStateFlow()

    private var currentFilter = MutableStateFlow(TVShowsFilterItem.FilterType.AIRING_TODAY)

    val tvShows = currentFilter.flatMapLatest { filter ->
        Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200, enablePlaceholders = true),
            initialKey = null,
            pagingSourceFactory = {
                TvShowsListPagingSource { page ->
                    val queryFilter = MoviesFilter(
                        language = "en-Us",
                        page = page,
                        region = null,
                    )

                    when (filter) {
                        TVShowsFilterItem.FilterType.AIRING_TODAY ->
                            tvShowsRepository.airingToday(queryFilter)

                        TVShowsFilterItem.FilterType.ON_THE_AIR ->
                            tvShowsRepository.onTheAir(queryFilter)

                        TVShowsFilterItem.FilterType.POPULAR ->
                            tvShowsRepository.popular(queryFilter)

                        TVShowsFilterItem.FilterType.TOP_RATED ->
                            tvShowsRepository.topRated(queryFilter)

                        TVShowsFilterItem.FilterType.DISCOVER ->
                            tvShowsRepository.airingToday(queryFilter)
                    }
                }
            },
        ).flow.cachedIn(viewModelScope)
    }.flowOn(dispatcher)

    fun onFilterChange(filter: TVShowsFilterItem.FilterType) {
        val updatedFilters = _tvShowFilters.value.map { filterItem ->
            if (filterItem.type == filter) {
                filterItem.copy(isSelected = true)
            } else {
                filterItem.copy(isSelected = false)
            }
        }
        currentFilter.update { filter }
        _tvShowFilters.update { updatedFilters }
    }
}
