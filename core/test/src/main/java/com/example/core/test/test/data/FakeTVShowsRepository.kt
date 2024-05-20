package com.example.core.test.test.data

import com.example.core.data.filter.MoviesFilter
import com.example.core.data.repository.TvShowsRepository
import com.example.core.model.TVShow
import com.example.core.network.model.response.asDomainObject
import com.example.core.test.util.TvShowsTestData
import javax.inject.Inject

class FakeTVShowsRepository
    @Inject
    constructor() : TvShowsRepository {
        override suspend fun airingToday(filter: MoviesFilter): List<TVShow> =
            TvShowsTestData.testNetworkTVShows().results.map { it.asDomainObject() }

        override suspend fun onTheAir(filter: MoviesFilter): List<TVShow> =
            TvShowsTestData.testNetworkTVShows().results.map { it.asDomainObject() }

        override suspend fun popular(filter: MoviesFilter): List<TVShow> =
            TvShowsTestData.testNetworkTVShows().results.map { it.asDomainObject() }

        override suspend fun topRated(filter: MoviesFilter): List<TVShow> =
            TvShowsTestData.testNetworkTVShows().results.map { it.asDomainObject() }
    }
