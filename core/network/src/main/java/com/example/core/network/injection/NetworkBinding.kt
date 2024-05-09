package com.example.core.network.injection

import com.example.core.network.datasource.DefaultPeopleListRemoteDataSource
import com.example.core.network.datasource.PeopleListRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBinding {
    @Binds
    abstract fun providesPeopleListRemoteDataSource(
        datasource: DefaultPeopleListRemoteDataSource,
    ): PeopleListRemoteDataSource
}
