package com.example.core.network.service

import com.example.core.network.Config
import com.example.core.network.model.response.NetworkMovie
import com.example.core.network.model.response.PagedNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListApi {
    /**
     * Get a list of movies that are currently in theatres.
     *
     * @param language the language for the query
     * @param page the current page of the query
     * @param region the region for the query
     * @return list of movies
     */
    @GET("${Config.TMDB_API_V3}/movie/now_playing")
    suspend fun nowPlaying(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkMovie>

    /**
     * Get a list of movies ordered by popularity.
     *
     * @param language the language of the query
     * @param page the current page for the query
     * @param region the region for the request
     * @return list of movies
     */
    @GET("${Config.TMDB_API_V3}/movie/popular")
    suspend fun popular(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkMovie>

    /**
     * Get a list of movies ordered by rating.
     *
     * @param language the language of the query
     * @param page the current page for the query
     * @param region the region for the request
     * @return list of movies
     */
    @GET("${Config.TMDB_API_V3}/movie/top_rated")
    suspend fun topRated(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkMovie>

    /**
     * Get a list of movies that are being released soon.
     *
     * @param language the language of the query
     * @param page the current page for the query
     * @param region the region for the request
     * @return list of movies
     */
    @GET("${Config.TMDB_API_V3}/movie/upcoming")
    suspend fun upcoming(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkMovie>
}
