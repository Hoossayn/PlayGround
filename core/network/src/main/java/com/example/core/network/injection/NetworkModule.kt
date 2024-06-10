package com.example.core.network.injection

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.core.common.injection.qualifier.AuthOkHttpClient
import com.example.core.common.injection.qualifier.NoAuthOkHttpClient
import com.example.core.network.AuthorizationInterceptor
import com.example.core.network.BuildConfig
import com.example.core.network.datasource.DefaultMovieDetailsRemoteDataSource
import com.example.core.network.datasource.DefaultMoviesRemoteDataSource
import com.example.core.network.datasource.MovieDetailsRemoteDataSource
import com.example.core.network.datasource.MoviesRemoteDataSource
import com.example.core.network.service.MovieDetailsApi
import com.example.core.network.service.MovieListApi
import com.example.core.network.service.PeopleListApi
import com.example.core.network.service.TVShowsListApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("TooManyFunctions")
object NetworkModule {
    @[Provides Singleton]
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
    ): ChuckerInterceptor {
        // Create the Collector
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR,
        )

        // Create the Interceptor
        @Suppress("MagicNumber")
        return ChuckerInterceptor.Builder(context).apply {
            collector(chuckerCollector)
            maxContentLength(250_000L)
            redactHeaders("Auth-Token", "Bearer")
            alwaysReadResponseBody(true)
            createShortcut(true)
        }.build()
    }

    @[Provides Singleton AuthOkHttpClient]
    fun provideAuthOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        val authToken = BuildConfig.TMDB_API_KEY
        val builder =
            OkHttpClient.Builder().addInterceptor(chuckerInterceptor)
                .addInterceptor(AuthorizationInterceptor(authToken))

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @[Provides Singleton NoAuthOkHttpClient]
    fun provideNoAuthOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor(chuckerInterceptor)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @[Provides Singleton]
    fun provideGson(): Gson = GsonBuilder().apply {
        setPrettyPrinting()
        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    }.create()

    @[Provides Singleton]
    fun provideRetrofit(
        @AuthOkHttpClient okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @[Provides Singleton]
    fun provideMoviesApi(retrofit: Retrofit): MovieListApi =
        retrofit.create(MovieListApi::class.java)

    @[Provides Singleton]
    fun provideMovieDetailsApi(retrofit: Retrofit): MovieDetailsApi =
        retrofit.create(MovieDetailsApi::class.java)

    @[Provides Singleton]
    fun provideMoviesRemoteDataSource(moviesApi: MovieListApi): MoviesRemoteDataSource =
        DefaultMoviesRemoteDataSource(moviesApi = moviesApi)

    @[Provides Singleton]
    fun provideMovieDetailsRemoteDataSource(
        movieDetailsApi: MovieDetailsApi,
    ): MovieDetailsRemoteDataSource =
        DefaultMovieDetailsRemoteDataSource(movieDetailsApi = movieDetailsApi)

    @[Provides Singleton]
    fun provideTvSeriesListsApi(retrofit: Retrofit): TVShowsListApi =
        retrofit.create(TVShowsListApi::class.java)

    @[Provides Singleton]
    fun providePeopleListsApi(retrofit: Retrofit): PeopleListApi =
        retrofit.create(PeopleListApi::class.java)

}
