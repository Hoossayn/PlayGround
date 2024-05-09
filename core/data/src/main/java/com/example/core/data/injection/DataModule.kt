package com.example.core.data.injection

import com.example.core.data.repository.DefaultMovieListRepository
import com.example.core.data.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun provideMoviesRepository(repository: DefaultMovieListRepository): MovieListRepository

}