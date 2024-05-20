package com.example.core.test.injection

import com.example.core.data.injection.DataModule
import com.example.core.data.repository.MovieListRepository
import com.example.core.data.repository.TvShowsRepository
import com.example.core.test.test.data.FakeMoviesRepository
import com.example.core.test.test.data.FakeTVShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
abstract class TestDataModule {
    @[Binds Singleton]
    abstract fun provideFakeMoviesRepository(repo: FakeMoviesRepository): MovieListRepository

    @[Binds Singleton]
    abstract fun provideTVShowsRepository(repo: FakeTVShowsRepository): TvShowsRepository

}