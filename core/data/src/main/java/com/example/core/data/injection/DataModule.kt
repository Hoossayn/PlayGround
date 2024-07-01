package com.example.core.data.injection

import com.example.core.data.repository.DefaultMovieDetailsRepository
import com.example.core.data.repository.DefaultMovieListRepository
import com.example.core.data.repository.DefaultPeopleListRepository
import com.example.core.data.repository.DefaultTvShowsRepository
import com.example.core.data.repository.MovieDetailsRepository
import com.example.core.data.repository.MovieListRepository
import com.example.core.data.repository.PeopleListRepository
import com.example.core.data.repository.TvShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun provideMoviesRepository(repository: DefaultMovieListRepository): MovieListRepository

    @Binds
    fun providesMovieDetailsRepository(
        repository: DefaultMovieDetailsRepository,
    ): MovieDetailsRepository

    @Binds
    fun provideTVShowsRepository(repo: DefaultTvShowsRepository): TvShowsRepository

    @Binds
    fun providePeoplesRepository(repository: DefaultPeopleListRepository): PeopleListRepository
}
