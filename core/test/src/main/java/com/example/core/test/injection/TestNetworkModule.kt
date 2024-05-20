package com.example.core.test.injection

import com.example.core.network.datasource.MoviesRemoteDataSource
import com.example.core.network.injection.NetworkModule
import com.example.core.network.service.TVShowsListApi
import com.example.core.test.test.network.FakeMoviesRemoteDataSource
import com.example.core.test.test.network.FakeTVSeriesListsApi
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class],
)
abstract class TestNetworkModule {

    @[Binds Singleton]
    abstract fun MoviesRemoteDataSource(
        dataSource: FakeMoviesRemoteDataSource
    ): MoviesRemoteDataSource

    @[Binds Singleton]
    abstract fun provideTVSeriesListsApi(repo: FakeTVSeriesListsApi): TVShowsListApi
}