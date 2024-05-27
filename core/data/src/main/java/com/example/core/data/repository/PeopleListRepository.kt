package com.example.core.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.common.injection.IoDispatcher
import com.example.core.model.Person
import com.example.core.network.datasource.PeopleListRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface PeopleListRepository {
    suspend fun popularPeople(page: Int): List<Person>
}

@Singleton
class DefaultPeopleListRepository
    @Inject
    constructor(
        private val peopleListRemoteDataSource: PeopleListRemoteDataSource,
        @IoDispatcher private val dispatcher: CoroutineDispatcher,
    ) : PeopleListRepository {
        override suspend fun popularPeople(page: Int): List<Person> =
            withContext(dispatcher) { peopleListRemoteDataSource.popularPersons(page) }
    }

class PeopleListPagingSource(private val peopleListRepository: PeopleListRepository) :
    PagingSource<Int, Person>() {
    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val pageNumber = params.key ?: 1
            val response = peopleListRepository.popularPeople(pageNumber)
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
