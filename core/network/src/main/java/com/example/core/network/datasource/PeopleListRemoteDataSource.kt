package com.example.core.network.datasource

import com.example.core.model.Person
import com.example.core.network.model.response.toDomainModel
import com.example.core.network.service.PeopleListApi
import javax.inject.Inject

interface PeopleListRemoteDataSource {
    suspend fun popularPersons(page: Int): List<Person>
}

class DefaultPeopleListRemoteDataSource
    @Inject
    constructor(private val peopleListsApi: PeopleListApi) : PeopleListRemoteDataSource {
        override suspend fun popularPersons(page: Int): List<Person> =
            peopleListsApi.popularPersons(page).results.map { it.toDomainModel() }
    }
