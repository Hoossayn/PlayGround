package com.example.core.data.injection

import com.example.core.data.repository.DefaultLocalStorage
import com.example.core.data.repository.LocalStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalStorageBinding {
    @Binds
    fun provideLocalStorage(storage: DefaultLocalStorage): LocalStorage
}
