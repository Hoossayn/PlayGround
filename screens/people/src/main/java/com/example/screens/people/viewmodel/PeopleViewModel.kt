package com.example.screens.people.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.core.common.injection.IoDispatcher
import com.example.core.data.repository.PeopleListPagingSource
import com.example.core.data.repository.PeopleListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel
    @Inject
    constructor(
        private val peopleListRepository: PeopleListRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        val persons = Pager(
            config = PagingConfig(pageSize = 4, maxSize = 200, enablePlaceholders = true),
            initialKey = null,
            pagingSourceFactory = {
                PeopleListPagingSource(peopleListRepository = peopleListRepository)
            },
        ).flow.cachedIn(viewModelScope)
            .flowOn(dispatcher)
    }
