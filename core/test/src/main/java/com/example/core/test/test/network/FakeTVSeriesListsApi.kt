package com.example.core.test.test.network

import com.example.core.network.model.response.NetworkTvShow
import com.example.core.network.model.response.PagedNetworkResponse
import com.example.core.network.service.TVShowsListApi
import com.example.core.test.util.TvShowsTestData
import javax.inject.Inject

class FakeTVSeriesListsApi
    @Inject
    constructor() : TVShowsListApi {
        override suspend fun airingToday(
            language: String,
            page: Int,
            region: String?,
        ): PagedNetworkResponse<NetworkTvShow> = TvShowsTestData.testNetworkTVShows()

        override suspend fun onTheAir(
            language: String,
            page: Int,
            region: String?,
        ): PagedNetworkResponse<NetworkTvShow> = TvShowsTestData.testNetworkTVShows()

        override suspend fun popular(
            language: String,
            page: Int,
            region: String?,
        ): PagedNetworkResponse<NetworkTvShow> = TvShowsTestData.testNetworkTVShows()

        override suspend fun topRated(
            language: String,
            page: Int,
            region: String?,
        ): PagedNetworkResponse<NetworkTvShow> = TvShowsTestData.testNetworkTVShows()
    }
