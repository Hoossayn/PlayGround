package com.example.core.test.injection

import com.example.core.common.injection.ApplicationScope
import com.example.core.common.injection.DefaultDispatcher
import com.example.core.common.injection.DispatchersModule
import com.example.core.common.injection.IoDispatcher
import com.example.core.common.injection.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatchersModule::class],
)
object TestDispatcherModule {
    @[Provides Singleton]
    fun providesTestDispatcher(): TestDispatcher = UnconfinedTestDispatcher()

    @[Provides Singleton]
    fun provideTestScope(testDispatcher: TestDispatcher): TestScope = TestScope(testDispatcher)

    @ApplicationScope
    @[Provides Singleton]
    fun providesCoroutineScope(testScope: TestScope): CoroutineScope = testScope

    @DefaultDispatcher
    @[Provides Singleton]
    fun providesDefaultDispatcher(testDispatcher: TestDispatcher): CoroutineDispatcher =
        testDispatcher

    @IoDispatcher
    @[Provides Singleton]
    fun providesIoDispatcher(testDispatcher: TestDispatcher): CoroutineDispatcher = testDispatcher

    @MainDispatcher
    @[Provides Singleton]
    fun providesMainDispatcher(testDispatcher: TestDispatcher): CoroutineDispatcher = testDispatcher
}
