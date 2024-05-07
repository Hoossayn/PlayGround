package com.example.core.data.injection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.core.data.repository.DefaultLocalStorage
import com.example.core.data.repository.UserPreferencesSerializer
import com.example.playground.core.data.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {
    @[Singleton Provides]
    fun provideUserPreferencesDatastore(
        @ApplicationContext context: Context,
    ): DataStore<UserPreferences> = DataStoreFactory.create(
        serializer = UserPreferencesSerializer(),
        produceFile = { context.dataStoreFile(DefaultLocalStorage.DATA_STORE_FILE_NAME) },
    )
}
